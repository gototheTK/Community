import React, { useState } from "react";
import "../style.css";
import {
  BrowserRouter,
  Route,
} from "react-router-dom/cjs/react-router-dom.min";

import BoardList from "./Board/BoardList";
import BoardPage from "./Board/BoardPage";

const Body = () => {
  return (
    <BrowserRouter>
      <Route path="/" exact={true} component={BoardList} />
      <Route path="/board/:id" exact={true} component={BoardPage} />
    </BrowserRouter>
  );
};

export default Body;
