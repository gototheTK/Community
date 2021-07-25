import React, { useContext, useState } from "react";
import { Navbar, Form, FormControl, Button, Col, Row } from "react-bootstrap";
import {
  ACCESS_TOKEN,
  Logout,
  REFRESH_TOKEN,
  USERNAME,
} from "../Functions/HttpMethod";
import { UserDispatch } from "../Home";
import JoinForm from "./Login/JoinForm";
import LoginForm from "./Login/LoginForm";

const Header = () => {
  const [isLogin, setIsLogin] = useState(false);
  const [isJoin, setIsJoin] = useState(false);
  const dispatch = useContext(UserDispatch);
  const [keyword, setKeyword] = useState("");

  const changeValue = (e) => {
    setKeyword(`${e.target.value}`);
  };

  const close = () => {
    setIsLogin(false);
    setIsJoin(false);
  };

  const toLoginForm = () => {
    setIsLogin(true);
    setIsJoin(false);
  };

  const toJoinForm = () => {
    setIsLogin(false);
    setIsJoin(true);
  };

  return (
    <>
      <Navbar
        className="row justify-content-center"
        sticky="top"
        expand="lg"
        bg="dark"
        variant="dark"
      >
        <Row className="d-flex-row justify-content-center">
          <Col className="d-flex justify-content-center">
            <Navbar.Brand href="/">Portfolio</Navbar.Brand>
          </Col>
          <Col className="col-6">
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

              <Button
                onClick={() => {
                  dispatch.setKeyword(keyword);
                }}
                size="lg"
                variant="outline-secondary"
              >
                Search
              </Button>
            </Form>
          </Col>

          <Col className="d-flex justify-content-center">
            {dispatch.user.active ? (
              <Button
                variant={"outline-secondary"}
                onClick={() => {
                  Logout();
                  dispatch.setUser({
                    username: null,
                    active: false,
                  });
                }}
              >
                로그아웃
              </Button>
            ) : (
              <Button variant={"outline-secondary"} onClick={toLoginForm}>
                로그인
              </Button>
            )}
          </Col>
        </Row>
      </Navbar>
      <>
        <LoginForm show={isLogin} move={toJoinForm} close={close} />
        <JoinForm show={isJoin} move={toLoginForm} close={close} />
      </>
    </>
  );
};

export default Header;
