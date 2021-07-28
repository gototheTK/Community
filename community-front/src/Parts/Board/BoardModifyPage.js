import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  ButtonToolbar,
  Card,
  CloseButton,
  Col,
  Container,
  Form,
  FormControl,
  Image,
  Row,
} from "react-bootstrap";
import {
  ACCESS_TOKEN,
  convertFormatToDate,
  DeleteRequest,
  GetRequest,
  HOST_DOMAIN,
  PostRequest,
  PutRequest,
  REFRESH_TOKEN,
  ResToken,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";
import { OUTLINE_LIGHT } from "../PartsConstants";
import ReplyForm from "./ReplyForm";
import ReplyItem from "./ReplyItem";

const BoardModifyPage = (props) => {
  const id = props.match.params.id;

  const dispatch = useContext(UserDispatch);

  const [board, setBoard] = useState({
    id: id,
    username: "",
    title: "",
    user: {},
    content: "",
    count: 0,
    createTime: "",
    boardFiles: [],
    interest: "",
  });

  const [imgBase64, setImgBase64] = useState([]);
  const [files, setFiles] = useState([]);

  const changeValue = (e) => {
    setBoard({ ...board, [e.target.name]: e.target.value });
  };

  const getBoard = () => {
    const name = "글가져오기";
    const domain = "/board/" + id;
    fetch(`${HOST_DOMAIN + domain}`, GetRequest())
      .then((response) => {
        if (response.status === 403) {
          dispatch.setUser({
            username: localStorage.getItem("username"),
            active: localStorage.getItem("username") !== null ? true : false,
          });
          alert("다시 로그인하여 주세요");
          props.history.push(`/board/${id}`);
        }
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        setBoard(response);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  const CancelBoardFiles = (id) => {
    const temp = board.boardFiles;

    temp.map((file) => {
      if (file.id === id) {
      } else {
        temp.pop(file);
        setBoard({ ...board, boardFiles: temp });
      }
    });
  };

  const cancelButton = (name) => {
    const temp1 = [];

    imgBase64.map((img) => {
      if (img.name === name) {
      } else {
        temp1.push(img);
      }
    });
    setImgBase64(temp1);

    const temp2 = [];
    files.map((file) => {
      if (file.name === name) {
      } else {
        temp2.push(file);
      }
    });
    setFiles(temp2);
  };

  const uploadFiles = (e) => {
    for (let i = 0; i < e.target.files.length; i++) {
      if (e.target.files[i]) {
        console.log(e.target.files[i]);
        setFiles([...files, e.target.files[i]]);
        let reader = new FileReader();
        reader.readAsDataURL(e.target.files[i]);
        reader.onloadend = () => {
          const base64 = reader.result;
          if (base64) {
            let name = e.target.files[i].name;
            let base64Sub = base64.toString();

            setImgBase64((imgBase64) => [
              ...imgBase64,
              { id: i, name: name, img: base64Sub },
            ]);
          }
        };
      }
    }
  };

  const modify = () => {
    const name = "글수정하기";
    const domain = "/api/board/modify";
    let multipleFiles = new FormData();

    multipleFiles.append(
      "board",
      new Blob([JSON.stringify(board)], {
        type: "application/json",
      })
    );

    if (files) {
      Object.values(files).forEach((file) => {
        multipleFiles.append("files", file);
      });
    }

    fetch(`${HOST_DOMAIN + domain}`, {
      method: "PUT",
      headers: {
        refresh_token: localStorage.getItem(REFRESH_TOKEN),
        access_token: localStorage.getItem(ACCESS_TOKEN),
      },
      body: multipleFiles,
    })
      .then((response) => {
        if (response.status === 403) {
          dispatch.setUser({
            username: localStorage.getItem("username"),
            active: localStorage.getItem("username") !== null ? true : false,
          });
          alert("다시 로그인하여 주세요");
          props.history.push(`/board/${id}`);
        }

        ResToken(response);
        setImgBase64([]);
        setFiles([]);
        return response.json();
      })
      .then((response) => {
        props.history.push(`/board/${id}`);
      })
      .catch((error) => {
        if (error.status === 403) {
        }
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  useEffect(() => {
    getBoard();
  }, []);

  return (
    <Container className="col-10">
      <Card
        boarder={"info"}
        bg={"dark"}
        text={"white"}
        style={{ position: "center" }}
      >
        <Form>
          <Card.Header>
            <strong>작성자 : {board.username}</strong>
            <Card.Subtitle className="d-flex justify-content-between">
              <small>조회수: {board.count}</small>
              <small>작성날짜: {convertFormatToDate(board.createTime)}</small>
            </Card.Subtitle>
          </Card.Header>
          <Card.Header>
            <Form.Group>
              제목
              <Form.Control
                style={{ background: "#212529", color: "white" }}
                name="title"
                value={board.title}
                onChange={changeValue}
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
              />
            </Form.Group>
          </Card.Header>
          <Card.Body>
            <Form.Group controlId="formTextArea" className="mb-3">
              내용
              <FormControl
                style={{ background: "#212529", color: "white" }}
                name="content"
                value={board.content}
                onChange={changeValue}
                as="textarea"
                aria-label="With textarea"
                rows={15}
              />
            </Form.Group>
            파일첨부
            <Form.Group controlId="formFile" className="mb-3">
              <FormControl
                style={{ background: "#212529", color: "white" }}
                name="files"
                onChange={uploadFiles}
                type="file"
                size="lg"
              />
            </Form.Group>
            <Row>
              <>
                {board.boardFiles.map((base64) => {
                  return (
                    <Col xs={6} md={4}>
                      <CloseButton
                        aria-label="Hide"
                        onClick={CancelBoardFiles}
                      ></CloseButton>
                      <Image
                        key={base64.id}
                        className="d-block w-100"
                        src={`${HOST_DOMAIN}/${base64.filePath}`}
                        rounded
                      />
                    </Col>
                  );
                })}
              </>
              <>
                {imgBase64.map((base64) => {
                  return (
                    <Col xs={6} md={4}>
                      <CloseButton
                        aria-label="Hide"
                        onClick={() => cancelButton(base64.name)}
                      ></CloseButton>
                      <Image
                        key={base64.id}
                        className="d-block w-100"
                        src={base64.img}
                        rounded
                      />
                    </Col>
                  );
                })}
              </>
            </Row>
          </Card.Body>

          <Card.Footer className="d-flex justify-content-between">
            <Button onClick={() => modify()} variant="outline-secondary">
              수정
            </Button>

            <Button
              onClick={() => props.history.push(`/board/${id}`)}
              variant={OUTLINE_LIGHT}
            >
              돌아가기
            </Button>
          </Card.Footer>
        </Form>
        <br></br>
      </Card>
    </Container>
  );
};

export default BoardModifyPage;
