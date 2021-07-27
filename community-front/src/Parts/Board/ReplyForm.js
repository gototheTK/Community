import React, { useState } from "react";
import { Button, FormControl } from "react-bootstrap";
import { HOST_DOMAIN, PostRequest, ResToken } from "../../Functions/HttpMethod";
import { OUTLINE_LIGHT } from "../PartsConstants";

const ReplyForm = ({ boardId, getReply, page }) => {
  const [reply, setReply] = useState({
    boardId: boardId,
    content: "",
  });

  const changeValue = (e) => {
    console.log(page);
    setReply({ ...reply, [e.target.name]: e.target.value });
  };

  const beforeWirte = (e) => {
    e.preventDefault();
    if (reply.content === "") {
      alert("댓글을 입력하여주세요");
    } else {
      writeReply();
    }
  };

  const writeReply = () => {
    const domain = "/api/board/reply/write";
    const name = "댓글쓰기";

    fetch(`${HOST_DOMAIN + domain}`, PostRequest(reply))
      .then((response) => {
        ResToken(response);
        return response.json();
      })
      .then((response) => {
        if (!response) {
        } else {
          setReply({ boardId: boardId, content: "" });
          getReply(page);
        }
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  return (
    <>
      <FormControl
        style={{ background: "#212529", color: "white" }}
        name="content"
        onChange={changeValue}
        as="textarea"
        aria-label="With textarea"
        rows={3}
        value={reply.content}
      ></FormControl>
      <Button
        className="d-flex-rows"
        onClick={beforeWirte}
        variant={OUTLINE_LIGHT}
      >
        <span>등 록</span>
      </Button>
    </>
  );
};

export default ReplyForm;
