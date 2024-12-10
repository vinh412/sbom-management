import { Typography } from "antd";
import dayjs from "dayjs";

export const convertBEtoFE = (data) => {
  let content = data.content.map((trans) => {
    return {
      ...trans,
      key: trans.id,
      fileId: trans.file.id,
      amount: trans.amount.toLocaleString() + "đ",
      dateCreated: dayjs(trans.dateCreated, "YYYY-MM-DD").format("DD/MM/YYYY"),
    };
  });
  return { ...data, content: content };
};

export const sortByOptions = [
  { value: "dateCreated", label: "Thời gian" },
  { value: "amount", label: "Số tiền" },
];

export const sortOrderOptions = [
  { value: "asc", label: "Tăng dần" },
  { value: "desc", label: "Giảm dần" },
];

export const rowsPerPageOptions = [
  { value: 10, label: "10" },
  { value: 20, label: "20" },
  { value: 50, label: "50" },
  { value: 100, label: "100" },
  { value: 200, label: "200" },
];

export const accountOptions = [
  {
    value: "ASC",
    label: "MAT TRAN TO QUOC VN - BAN CUU TRO TW"
  },
  { value: "DESC", label: "BAN VAN DONG CUU TRO TRUNG UONG" },
];

export const fileColumns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id"
  },
  {
    title: "Tên file",
    dataIndex: "filename",
    key: "filename"
  },
  {
    title: "Tên tài khoản",
    dataIndex: "accountName",
    key: "accountName"
  },
  {
    title: "Số tài khoản",
    dataIndex: "accountNo",
    key: "accountNo"
  },
  {
    title: "Ngân hàng",
    dataIndex: "bank",
    key: "bank"
  }
]
export const tableColumns = [
  {
    title: "Ngày chuyển",
    dataIndex: "dateCreated",
    key: "dateCreated",
  },
  {
    title: "Mã doc",
    dataIndex: "docNo",
    key: "docNo",
  },
  {
    title: "Số tiền chuyển",
    dataIndex: "amount",
    key: "amount",
  },
  {
    title: "Nội dung",
    dataIndex: "message",
    key: "message",
  },
  {
    title: "Người gửi",
    dataIndex: "sender",
    key: "sender",
  },
  {
    title: "File",
    dataIndex: "fileId",
    key: "fileId",
  },
  {
    title: "Trang",
    dataIndex: "page",
    key: "page",
  },
];

const preprocessText = (text, searchText) => {
  const insertText = '/mark'
  const lowerText = text.toLowerCase()
  const lowerSearchText = searchText.toLowerCase()
  const indexArray = []
  let index = lowerText.indexOf(lowerSearchText)
  while(index != -1){
    indexArray.push(index)
    index = lowerText.indexOf(lowerSearchText, index + 1)
  }

}