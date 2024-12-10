import { Button, Dropdown, Input, Pagination, Table } from "antd";
import {
  FaPlus,
  FaEllipsis,
  FaRegAddressCard,
  FaRegTrashCan,
} from "react-icons/fa6";
import React, { useEffect, useState } from "react";
import userApi from "../../api/user";
import { useNavigate } from "react-router-dom";

function UserManagementPage() {
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [sortBy, setSortBy] = useState("createdAt");
  const [order, setOrder] = useState("desc");
  const [users, setUsers] = useState([]);
  const [totalElements, setTotalElements] = useState(0);

  const navigate = useNavigate();
  const dropdownItem = [
    {
      label: "Detail",
      icon: <FaRegAddressCard />,
      key: "detail",
    },
    {
      label: "Delete",
      icon: <FaRegTrashCan color="red" />,
      key: "delete",
    },
  ];
  const dropdownMenu = {
    items: dropdownItem,
  };

  const columns = [
    {
      title: "Username",
      dataIndex: "username",
      key: "username",
    },
    {
      title: "Fullname",
      dataIndex: "fullname",
      key: "fullname",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Phone Number",
      dataIndex: "phoneNumber",
      key: "phoneNumber",
    },
    {
      title: "Created At",
      dataIndex: "createdAt",
      key: "createdAt",
    },
    {
      title: "More",
      key: "more",
      render: (text, record) => (
        <Dropdown
          menu={{
            ...dropdownMenu,
            onClick: (e) => {
              if (e.key === "detail") {
                navigate(`/admin/users/${record.username}`);
                console.log("detail", record);
              }
              else if (e.key === "delete") {
                console.log("delete", record);
              }
            },
          }}
          trigger={["click"]}
        >
          <Button type="text" icon={<FaEllipsis />} />
        </Dropdown>
      ),
    },
  ];

  useEffect(() => {
    const fetchUsers = async () => {
      const searchParams = new URLSearchParams({
        search,
        page,
        size,
        sortBy,
        order,
      });
      const data = await userApi.getUsers(searchParams);
      setUsers(data.content);
      setTotalElements(data.page.totalElements);
    };
    fetchUsers();
  }, [search, page, size, sortBy, order]);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "8px",
      }}
    >
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <Button type="primary" icon={<FaPlus />}>
          Create
        </Button>
        <Input.Search
          style={{ width: "300px" }}
          placeholder="Search"
          onSearch={(value) => setSearch(value)}
        />
      </div>
      <Table columns={columns} dataSource={users} pagination={false} />
      <Pagination
        current={page}
        total={totalElements}
        pageSize={size}
        showSizeChanger={true}
        align="center"
        onChange={(page, size) => {
          setPage(page);
          setSize(size);
        }}
      />
    </div>
  );
}

export default UserManagementPage;
