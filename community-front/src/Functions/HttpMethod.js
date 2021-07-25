// Host 정보
export const HOST_DOMAIN = "http://localhost:8080";
export const REFRESH_TOKEN = "refresh_token";
export const ACCESS_TOKEN = "access_token";
export const AUTHORIZATION = "Authorization";
export const USERNAME = "username";

//메소드
export const GET = "GET";
export const POST = "POST";
export const PUT = "PUT";
export const DELETE = "DELETE";

export const Logout = () => {
  localStorage.removeItem(REFRESH_TOKEN);
  localStorage.removeItem(USERNAME);
  localStorage.removeItem(ACCESS_TOKEN);
};

export const ResToken = (response) => {
  const access_token = response.headers.get(ACCESS_TOKEN);
  console.log(access_token);
  if (access_token !== null) {
    localStorage.setItem(ACCESS_TOKEN, access_token);
    console.log(localStorage.getItem(ACCESS_TOKEN));
  } else {
    Logout();
  }
};

export const GetRequest = () => {
  console.log(localStorage.getItem(REFRESH_TOKEN));
  console.log(localStorage.getItem(ACCESS_TOKEN));
  return {
    method: GET,
    headers: {
      "Content-Type": "application/json; charset=utf-8",
      refresh_token: localStorage.getItem(REFRESH_TOKEN),
      access_token: localStorage.getItem(ACCESS_TOKEN),
    },
  };
};

export const PostRequest = (body) => ({
  method: POST,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
    refresh_token: localStorage.getItem(REFRESH_TOKEN),
    access_token: localStorage.getItem(ACCESS_TOKEN),
  },
  body: JSON.stringify(body),
});

export const PutRequest = (body) => ({
  method: PUT,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
    refresh_token: localStorage.getItem(REFRESH_TOKEN),
    access_token: localStorage.getItem(ACCESS_TOKEN),
  },
  body: JSON.stringify(body),
});

export const DeleteRequest = () => ({
  method: DELETE,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
    refresh_token: localStorage.getItem(REFRESH_TOKEN),
    access_token: localStorage.getItem(ACCESS_TOKEN),
  },
});

//시간구하기
const DayHours = 1000 * 3600 * 24;

const digitFormat = (num) => {
  return ("0" + num).slice(-2);
};

export function convertFormatToDate(responseDate) {
  const timestamp = new Date(Date.parse(responseDate));
  const betweenTime = Date.now() - timestamp;

  const datePieces = `${digitFormat(timestamp.getFullYear())}-${digitFormat(
    timestamp.getMonth()
  )}-${digitFormat(timestamp.getDate())}`;
  const timepieces = `${digitFormat(timestamp.getHours())}:${digitFormat(
    timestamp.getMinutes()
  )}:${digitFormat(timestamp.getSeconds())}`;

  return betweenTime < DayHours ? timepieces : datePieces;
}
