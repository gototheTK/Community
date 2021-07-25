import React, { useContext } from "react";
import { Button, Card, OverlayTrigger, Popover } from "react-bootstrap";
import { UserDispatch } from "../../Home";
import ReplyForm from "./ReplyForm";

const ReplyItem = (props) => {
  const dispatch = useContext(UserDispatch);
  const { id, boardId, content, page, user } = props.reply;
  return (
    <Card
      size="sm"
      boarder={"info"}
      bg={"dark"}
      text={"white"}
      style={{ position: "center" }}
    >
      <Card.Body>
        {dispatch.user.active === true ? (
          <>
            <OverlayTrigger
              trigger="click"
              placement="right"
              rootClose
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
                    <ReplyForm
                      boardId={boardId}
                      getReply={props.getReply}
                      page={page}
                    />
                  </Popover.Content>
                </Popover>
              }
            >
              <Button variant="link">{user.username}</Button>
            </OverlayTrigger>
          </>
        ) : (
          <>{user.username}</>
        )}

        <Card.Text>{content}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default ReplyItem;
