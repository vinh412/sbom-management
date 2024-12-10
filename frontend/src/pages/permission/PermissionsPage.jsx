import React, { useEffect, useState } from 'react'
import permissionApi from '../../api/permission';
import { Input, Pagination, Table } from 'antd';

function PermissionsPage() {
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [sortBy, setSortBy] = useState("id");
  const [order, setOrder] = useState("desc");
  const [permissions, setPermissions] = useState([]);
  const [totalElements, setTotalElements] = useState(0);

  const columns = [
    {
      title: "Permission",
      dataIndex: "permission",
      key: "permission",
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
  ];

  useEffect(() => {
    const fetchPermissions = async () => {
      const searchParams = new URLSearchParams({
        search,
        page,
        size,
        sortBy,
        order,
      });
      const data = await permissionApi.getPermissions(searchParams);
      setPermissions(data.content);
      setTotalElements(data.page.totalElements);
    };
    fetchPermissions();
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
          justifyContent: "end",
        }}
      >
        <Input.Search
          style={{ width: "300px" }}
          placeholder="Search"
          onSearch={(value) => setSearch(value)}
        />
      </div>
      <Table columns={columns} dataSource={permissions} pagination={false} />
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
  )
}

export default PermissionsPage