import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  ButtonToolbar,
  Card,
  Container,
} from "react-bootstrap";
import { Link } from "react-router-dom/cjs/react-router-dom.min";
import {
  convertFormatToDate,
  DeleteRequest,
  GetRequest,
  HOST_DOMAIN,
  ResToken,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";
import { OUTLINE_LIGHT } from "../PartsConstants";
import ReplyForm from "./ReplyForm";
import ReplyItem from "./ReplyItem";

const BoardPage = (props) => {
  const id = props.match.params.id;
  const dispatch = useContext(UserDispatch);

  const [show, setShow] = useState(false);

  const [board, setBoard] = useState({
    id: id,
    username: "",
    title: "",
    user: {},
    content: "",
    count: 0,
    createTime: "",
    boardFiles: [],
  });

  const [resReply, setResReply] = useState({
    page: 0,
    pages: [],
    replys: [],
  });

  const [recommend, setRecommend] = useState(0);

  const getBoard = () => {
    const name = "글가져오기";
    const domain = "/board/" + id;
    fetch(`${HOST_DOMAIN + domain}`, GetRequest())
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        setBoard(response);
        setRecommend(response.recommend);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  const getReply = (page) => {
    const name = "댓글가져오기";
    const domain = `/board/${id}/reply/list?page=${page}`;
    fetch(`${HOST_DOMAIN + domain}`, GetRequest())
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        setResReply(response);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  const recommedBoard = () => {
    const name = "추천하기";
    const domain = "/api/board/recommend/" + id;
    fetch(`${HOST_DOMAIN + domain}`, GetRequest())
      .then((response) => {
        if (response.status === 403) {
          dispatch.setUser({
            username: localStorage.getItem("username"),
            active: localStorage.getItem("username") !== null ? true : false,
          });
          alert("다시 로그인하여 주세요");
        } else {
          ResToken(response);
        }
        return response.json();
      })
      .then((response) => {
        setRecommend(response);
      })
      .catch((error) => {
        alert(`${error} 그리하여 ${name}실패 하였습니다.`);
      });
  };

  const deleteBoard = () => {
    const name = "삭제하기";
    const domain = "/api/board/delete/" + id;
    fetch(`${HOST_DOMAIN + domain}`, DeleteRequest())
      .then((response) => {
        if (response.status === 403) {
          dispatch.setUser({
            username: localStorage.getItem("username"),
            active: localStorage.getItem("username") !== null ? true : false,
          });
          alert("다시 로그인하여 주세요");
        } else {
          ResToken(response);
        }
        if (response.status === 200) {
          alert("삭제가 완료되었습니다.");
          props.history.push("/");
        }
      })
      .catch((error) => {
        if (error.statusCode === 403) {
          alert("로그아웃되었습니다. 로그인해주세요.");
        } else {
          alert(`${error.statusCode} 그리하여 ${name}실패 하였습니다.`);
        }
      });
  };

  useEffect(() => {
    getBoard();
    getReply(resReply.page);
  }, []);

  return (
    <Container className="col-10">
      <Card
        boarder={"info"}
        bg={"dark"}
        text={"white"}
        style={{ position: "center" }}
      >
        <Card.Header>
          <Card.Title className="d-flex justify-content-between">
            <strong>{board.title}</strong>
            <strong>{board.username}</strong>
          </Card.Title>
        </Card.Header>
        <Card.Header>
          <Card.Subtitle className="d-flex justify-content-between">
            <small>조회수: {board.count}</small>
            <small>작성날짜: {convertFormatToDate(board.createTime)}</small>
          </Card.Subtitle>
        </Card.Header>
        <Card.Body>
          <Card.Text>{board.content}</Card.Text>
          {board.boardFiles.map((file) => {
            return (
              <>
                <Card.Img
                  key={file.id}
                  src={`${HOST_DOMAIN + "/" + file.filePath}`}
                />
              </>
            );
          })}
          <hr />
          <Card.Subtitle className="d-flex justify-content-center">
            <Button
              disabled={!dispatch.user.active}
              variant="outline-primary"
              onClick={recommedBoard}
            >
              추천 {recommend}
            </Button>
            {/* <Button variant="outline-danger">비추천 {board.recommend}</Button> */}
          </Card.Subtitle>

          {dispatch.user.username === board.username ? (
            <div className="d-flex justify-content-end">
              <Link to={"/board/modify/" + id}>
                <Button variant="outline-secondary">수정</Button>
              </Link>

              <Button onClick={deleteBoard} variant="outline-secondary">
                삭제
              </Button>
            </div>
          ) : (
            <></>
          )}
        </Card.Body>

        <hr />
        <Card.Body>
          {resReply.replys.map((reply) => {
            return (
              <>
                <ReplyItem key={reply.id} reply={reply} getReply={getReply} />
              </>
            );
          })}
        </Card.Body>
        <Card.Footer>
          <ButtonToolbar
            className="justify-content-center"
            aria-label="Toolbar with button groups"
          >
            <ButtonGroup className="me-2" aria-label="First group">
              {resReply.pages.map((page) => {
                return (
                  <Button
                    key={page}
                    onClick={() => getReply(page)}
                    variant={OUTLINE_LIGHT}
                  >
                    {page + 1}
                  </Button>
                );
              })}
            </ButtonGroup>
          </ButtonToolbar>
          <br />
        </Card.Footer>
        {dispatch.user.active === true ? (
          <Card.Footer className="d-flex">
            <ReplyForm boardId={id} getReply={getReply} page={resReply.page} />
          </Card.Footer>
        ) : (
          <></>
        )}
        <>
          <Card.Footer className="d-flex justify-content-between">
            <Button
              onClick={() => props.history.push("/")}
              variant={OUTLINE_LIGHT}
            >
              돌아가기
            </Button>
          </Card.Footer>
        </>

        <br></br>
      </Card>
    </Container>
  );
};

export default BoardPage;
