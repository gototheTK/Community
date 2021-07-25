import React, { createContext, useState } from "react";
import { Container } from "react-bootstrap";

import Header from "./Parts/Header";
import Body from "./Parts/Body";
import { GetRequest, HOST_DOMAIN, ResToken } from "./Functions/HttpMethod";

export const UserDispatch = createContext(null);

const Home = () => {
  // const [state, dispatch] = useReducer(reducer, user);

  const [user, setUser] = useState({
    username: localStorage.getItem("username"),
    active: localStorage.getItem("username") !== null ? true : false,
  });

  // 검색기능
  const [keyword, setKeyword] = useState("");

  return (
    <UserDispatch.Provider value={{ user, setUser, keyword, setKeyword }}>
      <Container>
        <Header user={user} />
        <br />
        <Body />
      </Container>
    </UserDispatch.Provider>
  );
};

export default Home;
