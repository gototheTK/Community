import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  Container,
  Form,
  FormControl,
  InputGroup,
  Modal,
  Nav,
  Tab,
  Tabs,
} from "react-bootstrap";
import {
  ACCESS_TOKEN,
  GetRequest,
  HOST_DOMAIN,
  POST,
  PostRequest,
  PUT,
  PutRequest,
  REFRESH_TOKEN,
  ResToken,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";
import { OUTLINE_LIGHT } from "../PartsConstants";

const UserModal = ({ show, setShow, userInterests, setUserInterests }) => {
  const dispatch = useContext(UserDispatch);

  const [item, setItem] = useState({
    password: "",
    passwordCheck: "",
  });

  const [interest, setInterest] = useState("");
  const [interests, setInterests] = useState([]);

  const changeInterest = (e) => {
    setInterest(e.target.value);
    console.log(interest);
  };

  const changeValue = (e) => {
    setItem({
      ...item,
      [e.target.name]: e.target.value,
    });
  };

  const deleteInterest = (userInterrest) => {
    const name = "유저관심사 삭제하기";
    const doamin = `${HOST_DOMAIN}/api/user/interest/delete`;
    fetch(doamin, PutRequest(userInterrest))
      .then((response) => {
        ResToken(response);
        console.log(response.status);
        if (response.status === 200) {
          const temp = [];
          userInterests.map((interest) => {
            if (userInterrest.id === interest.id) {
            } else {
              temp.push(interest);
            }
          });
          setUserInterests(temp);
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
        if (!response) {
        } else if (!response.status) {
          setUserInterests(response);
        }
      })
      .catch((err) => {
        alert(`${err}때문에 ${name}에 실패하였습니다.`);
      });
  };

  const writeUserInterest = (usersinterest) => {
    const name = "유저관심사 등록하기";
    const doamin = `${HOST_DOMAIN}/api/user/interest/write`;
    if (!usersinterest) {
    } else {
      fetch(doamin, PostRequest(usersinterest))
        .then((response) => {
          ResToken(response);
          return response.json();
        })
        .then((response) => {
          if (!response) {
          } else if (!response.status) {
            setUserInterests([...userInterests, response]);
          } else {
            alert(`이미 등록하셨습니다.`);
          }
        })
        .catch((err) => {
          alert(`${err}때문에 ${name}에 실패하였습니다.`);
        });
    }
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
          setInterests(response);
        }
      })
      .catch((err) => {
        alert(`${err}때문에 ${name}에 실패하였습니다.`);
      });
  };

  const writeInterest = (e) => {
    e.preventDefault();
    const name = "관심사 등록하기";
    const doamin = `${HOST_DOMAIN}/api/interest/wirte`;
    if (!interest) {
    } else {
      fetch(doamin, {
        method: POST,
        headers: {
          "Content-Type": "application/json; charset=utf-8",
          refresh_token: localStorage.getItem(REFRESH_TOKEN),
          access_token: localStorage.getItem(ACCESS_TOKEN),
        },
        body: interest,
      })
        .then((response) => {
          ResToken(response);
          switch (response.status) {
            case 500:
              alert(`${interest}가 이미 존재합니다.`);
              break;
            default:
              alert(`${interest}등록에 성공하였습니다.`);
              getInterests();
          }
        })
        .catch((err) => {
          alert(`${err}때문에 ${name}에 실패하였습니다.`);
        });
    }
  };

  useEffect(() => {
    getInterests();
    getUserInterests();
  }, []);

  return (
    <Modal
      onHide={() => setShow(false)}
      show={show}
      backdrop="static"
      keyboard={false}
      size="md"
      aria-labelledby="contained-modal-title-lg"
      centered
    >
      <Tab.Container id="left-tabs-example" defaultActiveKey="mypage">
        <Modal.Header
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
          className="justify-content-between"
          closeButton
        >
          <Nav.Item>
            <Nav.Link eventKey="mypage">
              <Modal.Title
                style={{ background: "#212529", color: "white" }}
                id="contained-modal-title-vcenter"
              >
                마이페이지
              </Modal.Title>
            </Nav.Link>
          </Nav.Item>

          <Nav.Item>
            <Nav.Link eventKey="interest">
              <Modal.Title
                style={{ background: "#212529", color: "white" }}
                id="contained-modal-title-vcenter"
              >
                관심사
              </Modal.Title>
            </Nav.Link>
          </Nav.Item>
        </Modal.Header>
        <Tab.Content>
          <Tab.Pane eventKey="mypage">
            <Modal.Body>
              <InputGroup size="lg">
                <InputGroup.Text
                  id="inputGroup-sizing-lg"
                  style={{ background: "#212529", color: "white" }}
                >
                  유 저 아 이 디
                </InputGroup.Text>
                <FormControl
                  style={{ background: "#212529", color: "white" }}
                  name="username"
                  onChange={changeValue}
                  aria-label="Default"
                  aria-describedby="inputGroup-sizing-default"
                  disabled
                  value={dispatch.user.username}
                />
              </InputGroup>
              <hr />
              <InputGroup size="lg">
                <InputGroup.Text
                  id="inputGroup-sizing-lg"
                  style={{ background: "#212529", color: "white" }}
                >
                  비밀번호수정
                </InputGroup.Text>
                <FormControl
                  style={{ background: "#212529", color: "white" }}
                  type="password"
                  name="passwordCheck"
                  onChange={changeValue}
                  aria-label="Default"
                  aria-describedby="inputGroup-sizing-default"
                />
              </InputGroup>
              <hr />
              <InputGroup size="lg">
                <InputGroup.Text
                  id="inputGroup-sizing-lg"
                  style={{ background: "#212529", color: "white" }}
                >
                  비밀번호확인
                </InputGroup.Text>
                <FormControl
                  style={{ background: "#212529", color: "white" }}
                  type="password"
                  name="password"
                  onChange={changeValue}
                  aria-label="Default"
                  aria-describedby="inputGroup-sizing-default"
                />
              </InputGroup>
            </Modal.Body>
            <Modal.Footer
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Button
                size="lg"
                variant="outline-light"
                type="submit"
                onClick={() => {}}
              >
                회원정보수정
              </Button>
            </Modal.Footer>
          </Tab.Pane>
          <Tab.Pane eventKey="interest">
            <Modal.Body>
              <ButtonGroup siza="lg">
                <Container
                  style={{
                    "overflow-y": "auto",
                    "max-height": "200px",
                  }}
                >
                  {userInterests.map((userInterrest) => {
                    return (
                      <Button
                        onClick={() => deleteInterest(userInterrest)}
                        variant={OUTLINE_LIGHT}
                      >
                        <h5>{userInterrest.name}</h5>
                      </Button>
                    );
                  })}
                </Container>
              </ButtonGroup>
              <hr />
              <Container>
                <Form className="d-flex">
                  <FormControl
                    size="lg"
                    type="search"
                    name="keyword"
                    className="md-6"
                    placeholder="Search"
                    aria-label="Search"
                    style={{ background: "#212529", color: "white" }}
                    onChange={changeValue}
                  />

                  <Button size="lg" variant="outline-secondary">
                    Search
                  </Button>
                </Form>
                <Container
                  className="d-grid gap-0"
                  style={{ "overflow-y": "auto", "max-height": "200px" }}
                >
                  {interests.map((interest) => {
                    return (
                      <Button
                        onClick={() => writeUserInterest(interest)}
                        variant={OUTLINE_LIGHT}
                      >
                        <h5>{interest.name}</h5>
                      </Button>
                    );
                  })}
                </Container>
              </Container>
            </Modal.Body>
            <Modal.Footer>
              <Container>
                <h5>관심사 등록</h5>
                <Form className="d-flex">
                  <FormControl
                    size="lg"
                    type="search"
                    name="keyword"
                    className="md-6"
                    placeholder="Search"
                    aria-label="Search"
                    style={{ background: "#212529", color: "white" }}
                    onChange={changeInterest}
                  />

                  <Button
                    onClick={writeInterest}
                    size="lg"
                    variant="outline-secondary"
                  >
                    Enroll
                  </Button>
                </Form>
              </Container>
            </Modal.Footer>
          </Tab.Pane>
        </Tab.Content>
      </Tab.Container>
    </Modal>
  );
};

export default UserModal;
