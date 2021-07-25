import React, { useContext, useState } from "react";
import { Button, FormControl, InputGroup, Modal } from "react-bootstrap";
import {
  ACCESS_TOKEN,
  HOST_DOMAIN,
  PostRequest,
  REFRESH_TOKEN,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";

const LoginForm = ({ show, move, close }) => {
  const dispatch = useContext(UserDispatch);

  const [item, setItem] = useState({
    username: "",
    password: "",
  });

  const changeValue = (e) => {
    setItem({ ...item, [e.target.name]: e.target.value });
  };

  const login = (e) => {
    e.preventDefault();
    const name = "로그인";
    const domain = "/login";
    fetch(`${HOST_DOMAIN + domain}`, PostRequest(item))
      .then((response) => {
        if (response.status === 200) {
          localStorage.setItem(
            ACCESS_TOKEN,
            response.headers.get(ACCESS_TOKEN)
          );
          localStorage.setItem(
            REFRESH_TOKEN,
            response.headers.get(REFRESH_TOKEN)
          );
          localStorage.setItem("username", item.username);
          close();
          dispatch.setUser({
            username: localStorage.getItem("username"),
            active: true,
          });
        }
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  return (
    <Modal
      size="md"
      aria-labelledby="contained-modal-title-lg"
      centered
      show={show}
      onHide={close}
    >
      <Modal.Header
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Modal.Title
          style={{ background: "#212529", color: "white" }}
          id="contained-modal-title-vcenter"
        >
          로그인
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <InputGroup size="lg">
          <InputGroup.Text
            id="inputGroup-sizing-lg"
            style={{ background: "#212529", color: "white" }}
          >
            유저아이디
          </InputGroup.Text>
          <FormControl
            style={{ background: "#212529", color: "white" }}
            name="username"
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
            비 밀 번 호
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
        <Button size="lg" variant="outline-light" type="submit" onClick={login}>
          로그인
        </Button>

        <Button size="lg" variant="outline-light" onClick={move}>
          회원가입
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default LoginForm;
