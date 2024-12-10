import { Button, Input, Pagination, Table } from "antd";
import React, { useEffect, useState } from "react";
import projectApi from "../../api/project";
import { FaPlus } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";

function ProjectsPage() {
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [sortBy, setSortBy] = useState("createdAt");
  const [order, setOrder] = useState("asc");
  const [projects, setProjects] = useState([]);
  const [totalElements, setTotalElements] = useState(0);

  const navigate = useNavigate();

  const columns = [
    {
      title: "No",
      dataIndex: "no",
      key: "no",
    },
    {
      title: "Project Name",
      dataIndex: "name",
      key: "name",
      render: (item) => {
        return (
          <Button type="link" onClick={() => navigate(`/projects/${item}`)}>
            {item}
          </Button>
        );
      },
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Created At",
      dataIndex: "createdAt",
      key: "createdAt",
    },
  ];

  useEffect(() => {
    const fetchProjects = async () => {
      setLoading(true);
      const searchParams = new URLSearchParams({
        search,
        page,
        size,
        sortBy,
        order,
      });
      try {
        const data = await projectApi.getProjects(searchParams);
        setProjects(
          data.content.map((item, index) => {
            return {
              ...item,
              no: index + 1 + (page - 1) * size,
            };
          })
        );
        setTotalElements(data.page.totalElements);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
    fetchProjects();
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
      <Table columns={columns} dataSource={projects} pagination={false} />
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

export default ProjectsPage;
