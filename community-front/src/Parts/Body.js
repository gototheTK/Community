import React, { useState } from "react";
import "../style.css";
import {
  BrowserRouter,
  Route,
} from "react-router-dom/cjs/react-router-dom.min";

import BoardList from "./Board/BoardList";
import BoardPage from "./Board/BoardPage";
import BoardModifyPage from "./Board/BoardModifyPage";

const Body = () => {
  return (
    <BrowserRouter>
      <Route path="/" exact={true} component={BoardList} />
      <Route path="/?keyword=:keyword" component={BoardList} />
      <Route path="/board/:id" exact={true} component={BoardPage} />
      <Route
        path="/board/modify/:id"
        exact={true}
        component={BoardModifyPage}
      />
    </BrowserRouter>
  );
};

export default Body;
