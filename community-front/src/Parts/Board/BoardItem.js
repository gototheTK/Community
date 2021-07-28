import React from "react";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom/cjs/react-router-dom.min";
import { convertFormatToDate, HOST_DOMAIN } from "../../Functions/HttpMethod";

const BoardItem = (props) => {
  const item = props.item;

  return (
    <>
      <Card
        key={item.id}
        boarder={"info"}
        bg={"dark"}
        text={"white"}
        style={{ position: "center" }}
        className="text-center"
      >
        <Card.Header className="d-flex justify-content-between">
          <strong>추천수:{item.recommend}</strong>

          <span> 작정자: {item.user.username}</span>
        </Card.Header>
        <Card.Header className="d-flex justify-content-between">
          <span>{item.interest.name}</span>
          <span>댓글수: {item.replys.length}</span>
        </Card.Header>
        <br />
        <Card.Title>
          <h4>{item.title}</h4>
        </Card.Title>
        <Card.Body>
          <Link to={"/board/" + item.id}>
            <Card.Text>{item.content}</Card.Text>

            {item.boardFiles.map((file) => {
              return (
                <Card.Img
                  key={file.id}
                  src={`${HOST_DOMAIN + "/" + file.filePath}`}
                />
              );
            })}
          </Link>
        </Card.Body>
        <Card.Footer className="d-flex justify-content-between text-muted">
          <span>조회수:{item.count}</span>
          <span>작성날짜:{convertFormatToDate(item.createTime)}</span>
        </Card.Footer>
      </Card>
      <br />
    </>
  );
};

export default BoardItem;
