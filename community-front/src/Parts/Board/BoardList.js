import React, { useContext, useEffect, useState } from "react";
import { ButtonGroup, Container, Button, Pagination } from "react-bootstrap";
import BoardItem from "./BoardItem";
import { OUTLINE_LIGHT } from "../PartsConstants";
import BoardModal from "./BoardModal";
import UserModal from "../User/UserModal";
import {
  GET,
  ACCESS_TOKEN,
  HOST_DOMAIN,
  REFRESH_TOKEN,
  ResToken,
  GetRequest,
  PostRequest,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";

const BoardList = () => {
  const [show, setShow] = useState(false);
  const [userShow, setUserShow] = useState(false);
  const [items, setItems] = useState({
    page: 0,
    total: 0,
    pages: [],
    boards: [],
  });

  const [userInterests, setUserInterests] = useState([]);

  const dispatch = useContext(UserDispatch);
  const keyword = dispatch.keyword;
  const getBoardList = (page) => {
    const name = "글목록가져오기";
    const domain = `/board/list?page=${page}&keyword=${keyword}`;
    fetch(`${HOST_DOMAIN + domain}`, {
      method: GET,
      headers: {
        "Content-Type": "application/json; charset=utf-8",
        refresh_token: localStorage.getItem(REFRESH_TOKEN),
        access_token: localStorage.getItem(ACCESS_TOKEN),
      },
    })
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        setItems(response);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  const getInterests = (e) => {
    const name = "관심사 가져오기";
    const doamin = `${HOST_DOMAIN}/interest/list`;
    fetch(doamin, GetRequest())
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        if (!response) {
        } else {
          setUserInterests(response);
        }
      })
      .catch((err) => {
        alert(`${err}때문에 ${name}에 실패하였습니다.`);
      });
  };

  const getUserInterests = (e) => {
    const name = "유저관심사 가져오기";
    const doamin = `${HOST_DOMAIN}/api/user/interest/list`;
    fetch(doamin, GetRequest())
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        if (!response) {
        } else if (!response.status) {
          setUserInterests(response);
        }
      })
      .catch((err) => {
        alert(`${err}때문에 ${name}에 실패하였습니다.`);
      });
  };

  const getBoardsOfInterest = (page, interset) => {
    const name = "관심사글목록가져오기";
    const domain = `/board/list/interest?page${page}`;
    fetch(`${HOST_DOMAIN + domain}`, PostRequest(interset))
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        setItems(response);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  const getBoardsByRecommend = (page) => {
    const name = "추천순글목록가져오기";
    const domain = `/board/list/recommend?page${page}`;
    fetch(`${HOST_DOMAIN + domain}`, GetRequest())
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        console.log(response);
        setItems(response);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  useEffect(() => {
    dispatch.user.active === true ? getUserInterests() : getInterests();

    getBoardList(0);
  }, [keyword]);

  return (
    <>
      <BoardModal
        show={show}
        setShow={setShow}
        getBoardList={getBoardList}
        userInterests={userInterests}
      />
      <UserModal
        show={userShow}
        setShow={setUserShow}
        userInterests={userInterests}
        setUserInterests={setUserInterests}
      />
      <Container style={{ marginLeft: 0 }} className="col-10">
        {items.boards.map((item) => {
          return <BoardItem key={item.id} item={item} />;
        })}
        <Container className="d-flex justify-content-center">
          <Pagination>
            <Pagination.First onClick={() => getBoardList(0)} />

            {items.pages.map((page) => {
              if (page === items.page) {
                return (
                  <Pagination.Item
                    key={page}
                    active
                    onClick={() => getBoardList(page)}
                  >
                    {page + 1}
                  </Pagination.Item>
                );
              }

              return (
                <Pagination.Item key={page} onClick={() => getBoardList(page)}>
                  {page + 1}
                </Pagination.Item>
              );
            })}

            <Pagination.Last onClick={() => getBoardList(items.total - 1)} />
          </Pagination>
        </Container>
      </Container>

      <Container
        className="col-3 d-flex fixed-top flex-column align-items-start bd-highlight"
        style={{ marginLeft: "78%", marginTop: "3%" }}
      >
        <br />
        <br />

        <br />
        {dispatch.user.active === true ? (
          <>
            <Button
              onClick={() => setUserShow(true)}
              username={dispatch.user.username}
              variant={OUTLINE_LIGHT}
            >
              <h5>마이페이지</h5>
            </Button>
            <br />
            <Button onClick={() => setShow(true)} variant={OUTLINE_LIGHT}>
              <h5>글쓰기</h5>
            </Button>
          </>
        ) : (
          <></>
        )}
        <br />
        <ButtonGroup siza="lg" vertical>
          <Button onClick={() => getBoardList(0)} variant={OUTLINE_LIGHT}>
            <h5>최신</h5>
          </Button>
          <Button
            onClick={() => getBoardsByRecommend(0)}
            variant={OUTLINE_LIGHT}
          >
            <h5>화제</h5>
          </Button>
          {userInterests.map((interest) => {
            return (
              <Button
                key={interest.id}
                onClick={() => getBoardsOfInterest(0, interest)}
                variant={OUTLINE_LIGHT}
              >
                <h5>{interest.name}</h5>
              </Button>
            );
          })}
        </ButtonGroup>

        {/* <ButtonGroup siza="lg" vertical>
          <br />
          <Button variant={OUTLINE_LIGHT}>
            <h5>작상한댓글</h5>
          </Button>
          <br />
          <Button variant={OUTLINE_LIGHT}>
            <h5>작성한글</h5>
          </Button>
          <br />
        </ButtonGroup> */}
        <br />
        <br />
      </Container>
    </>
  );
};

export default BoardList;
