import React, { useContext, useState } from "react";
import {
  Button,
  Col,
  Form,
  FormControl,
  Modal,
  Row,
  Image,
  CloseButton,
  Container,
  ButtonGroup,
  ToggleButton,
} from "react-bootstrap";
import {
  ACCESS_TOKEN,
  HOST_DOMAIN,
  REFRESH_TOKEN,
  ResToken,
} from "../../Functions/HttpMethod";
import { UserDispatch } from "../../Home";
import Header from "../Header";
import { OUTLINE_LIGHT } from "../PartsConstants";

const max_width = `"max-width": "1000px"`;
const style = `opacity: 0.9, "background-color": "#212529", color: "white"`;

const BoardModal = ({ userInterests, show, setShow, getBoardList }) => {
  const dispatch = useContext(UserDispatch);

  const [radioValue, setRadioValue] = useState(1);

  const [board, setBoard] = useState({
    title: "",
    content: "",
    interest: {},
  });

  const [imgBase64, setImgBase64] = useState([{}]);
  const [files, setFiles] = useState([]);

  const changeValue = (e) => {
    setBoard({ ...board, [e.target.name]: e.target.value });
    console.log(board);
  };

  const changeInterest = (userInterrest) => {
    console.log(userInterrest);
    setBoard({
      ...board,
      interest: userInterrest,
    });
    setRadioValue(userInterrest.id);
  };

  const cancelButton = (name) => {
    const temp1 = [];

    imgBase64.map((img) => {
      if (img.name === name) {
      } else {
        temp1.push(img);
      }
    });
    setImgBase64(temp1);

    const temp2 = [];
    files.map((file) => {
      if (file.name === name) {
      } else {
        temp2.push(file);
      }
    });
    setFiles(temp2);
  };

  const uploadFiles = (e) => {
    for (let i = 0; i < e.target.files.length; i++) {
      if (e.target.files[i]) {
        console.log(e.target.files[i]);
        setFiles([...files, e.target.files[i]]);
        console.log("파일" + files);
        console.log(files);
        let reader = new FileReader();
        reader.readAsDataURL(e.target.files[i]);
        reader.onloadend = () => {
          const base64 = reader.result;
          if (base64) {
            let name = e.target.files[i].name;
            let base64Sub = base64.toString();

            setImgBase64((imgBase64) => [
              ...imgBase64,
              { id: i, name: name, img: base64Sub },
            ]);
          }
        };
      }
    }
  };

  const beforeWirte = (e) => {
    e.preventDefault();
    if (board.title === "" || board.content === "") {
      alert("제목과 글을 입력하여주세요");
    } else {
      write();
    }
  };

  const write = () => {
    console.log(files);

    const name = "글쓰기";
    const domain = "/api/board/write";
    let multipleFiles = new FormData();

    if (files) {
      Object.values(files).forEach((file) => {
        multipleFiles.append("files", file);
      });
    }

    userInterests.map((interest) => {
      if (radioValue === interest.id + "") {
        setBoard({ ...board, interest: interest });
      }
    });

    console.log("게시판:" + board);
    console.log(board);

    multipleFiles.append(
      "board",
      new Blob([JSON.stringify(board)], {
        type: "application/json",
      })
    );

    fetch(`${HOST_DOMAIN + domain}`, {
      method: "POST",
      headers: {
        refresh_token: localStorage.getItem(REFRESH_TOKEN),
        access_token: localStorage.getItem(ACCESS_TOKEN),
      },
      body: multipleFiles,
    })
      .then((response) => {
        ResToken(response);
        setImgBase64([]);
        setFiles([]);
        return response.json();
      })
      .then((response) => {
        getBoardList();
        setShow(false);
      })
      .catch((error) => {
        alert(`${error}의문제 때문에 ${name}에 실패하였습니다.`);
      });
  };

  return (
    <Modal
      size="lg"
      onHide={() => setShow(false)}
      show={show}
      style={{ max_width }}
      backdrop="static"
      keyboard={false}
      centered
    >
      <Modal.Header className="justify-content-between" closeButton>
        <h5>글쓴이 : {dispatch.user.username}</h5>
      </Modal.Header>
      <Container>
        <Container>
          <ButtonGroup className="mb-2">
            <Container>
              {userInterests.map((userInterrest) => {
                console.log(radioValue === userInterrest.id);
                return radioValue === userInterrest.id ? (
                  <Button
                    key={userInterrest.id}
                    variant="secondary"
                    id={userInterrest.id}
                    value={userInterrest.id}
                    type="radio"
                    name="interest"
                    active={true}
                    onClick={() => changeInterest(userInterrest)}
                  >
                    {userInterrest.name}
                  </Button>
                ) : (
                  <Button
                    key={userInterrest.id}
                    variant="outline-secondary"
                    id={userInterrest.id}
                    value={userInterrest}
                    onClick={() => changeInterest(userInterrest)}
                    type="radio"
                    name="interest"
                  >
                    {userInterrest.name}
                  </Button>
                );
              })}
            </Container>
          </ButtonGroup>
        </Container>
      </Container>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formBasic" className="mb-3">
            제목
            <FormControl
              style={{ background: "#212529", color: "white" }}
              name="title"
              onChange={changeValue}
              aria-label="Default"
              aria-describedby="inputGroup-sizing-default"
            />
          </Form.Group>
          <Form.Group controlId="formTextArea" className="mb-3">
            내용
            <FormControl
              style={{ background: "#212529", color: "white" }}
              name="content"
              onChange={changeValue}
              as="textarea"
              aria-label="With textarea"
              rows={15}
            />
          </Form.Group>
          파일첨부
          <Form.Group controlId="formFile" className="mb-3">
            <FormControl
              style={{ background: "#212529", color: "white" }}
              name="files"
              onChange={uploadFiles}
              type="file"
              size="lg"
            />
          </Form.Group>
          <Row>
            {imgBase64.map((base64) => {
              return (
                <Col key={base64.id} xs={6} md={4}>
                  <CloseButton
                    aria-label="Hide"
                    onClick={() => cancelButton(base64.name)}
                  ></CloseButton>
                  <Image
                    key={base64.id}
                    className="d-block w-100"
                    src={base64.img}
                    rounded
                  />
                </Col>
              );
            })}
          </Row>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant={OUTLINE_LIGHT} onClick={beforeWirte}>
          글쓰기
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default BoardModal;
