import React, { useState } from "react";
import { Button, FormControl, InputGroup, Modal } from "react-bootstrap";
import { HOST_DOMAIN, PostRequest, Logout } from "../../Functions/HttpMethod";

const JoinForm = ({ show, move, close }) => {
  const [item, setItem] = useState({
    username: "",
    password: "",
    passwordCheck: "",
  });

  const changeValue = (e) => {
    setItem({ ...item, [e.target.name]: e.target.value });
  };

  const join = (e) => {
    e.preventDefault();
    const name = "회원가입";
    const domain = "/join";
    fetch(`${HOST_DOMAIN + domain}`, PostRequest(item))
      .then((response) => {
        return response.json();
      })
      .then((response) => {
        if (response === 200) {
          alert("아이디가 이미 존재합니다");
        } else if (response === 201) {
          alert("회원가입이 완료되었습니다.");
          Logout();
          move();
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
          회원가입
        </Modal.Title>
      </Modal.Header>
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
          />
        </InputGroup>
        <hr />
        <InputGroup size="lg">
          <InputGroup.Text
            id="inputGroup-sizing-lg"
            style={{ background: "#212529", color: "white" }}
          >
            비밀번호입력
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
            name="passwordCheck"
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
        <Button size="lg" variant="outline-light" type="submit" onClick={join}>
          회원가입
        </Button>

        <Button size="lg" variant="outline-light" onClick={move}>
          이전
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default JoinForm;
