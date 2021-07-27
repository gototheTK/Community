import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  Card,
  FormControl,
  OverlayTrigger,
  Popover,
} from "react-bootstrap";
import { HOST_DOMAIN, PostRequest, ResToken } from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";
import { OUTLINE_LIGHT } from "../PartsConstants";

const ReplyItem = (props) => {
  const dispatch = useContext(UserDispatch);
  const { id, board, content, page, user, parent, groupId } = props.reply;

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [reply, setReply] = useState({
    parentId: id,
    boardId: board.id,
    groupId: groupId,
    content: "",
  });

  const changeValue = (e) => {
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
    const domain = "/api/board/reply/writeto";
    const name = "대댓글쓰기";
    fetch(`${HOST_DOMAIN + domain}`, PostRequest(reply))
      .then((response) => {
        handleClose();

        ResToken(response);
        return response.json();
      })
      .then((response) => {
        if (response === 201) {
          setReply({ ...reply, content: "" });
          props.getReply(page);
        }
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  return (
    <Card
      size="sm"
      boarder={"info"}
      bg={"dark"}
      text={"white"}
      style={{ position: "center" }}
    >
      <Card.Body>
        {dispatch.user.active === true &&
        user.username !== dispatch.user.username ? (
          <>
            <OverlayTrigger
              trigger="click"
              rootClose
              onToggle={handleClose}
              placement="right"
              show={show}
              onHide={handleClose}
              overlay={
                <Popover
                  style={{
                    background: "#212529",
                    color: "white",
                    opacity: "0.9",
                    "max-width": "700px",
                  }}
                  name="content"
                  id="popover-basic"
                >
                  <Popover.Title
                    style={{ background: "#212529", color: "white" }}
                    name="id"
                    as="h3"
                    className="d-flex justify-content-between"
                  >
                    <>{user.username}님에게 댓글쓰기</>
                  </Popover.Title>
                  <Popover.Content className="d-flex justify-content-between">
                    <FormControl
                      key={id}
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
                  </Popover.Content>
                </Popover>
              }
            >
              <>
                <Button onClick={handleShow} variant="link">
                  {user.username}
                </Button>
              </>
            </OverlayTrigger>
          </>
        ) : (
          <>
            <Button disabled variant="link">
              {user.username}
            </Button>
          </>
        )}
        {parent === null ? "" : ` @ ${parent.user.username}`}
        <Card.Text>{content}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default ReplyItem;
