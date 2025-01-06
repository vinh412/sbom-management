import { Button, Input, message, Pagination, Popconfirm, Table } from "antd";
import React, { useEffect, useState } from "react";
import projectApi from "../../api/project";
import { FaRegPenToSquare, FaRegTrashCan } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";
import CreateProject from "./CreateProject";
import EditProject from "./EditProject";

function ProjectsPage() {
  const [loading, setLoading] = useState(false);
  const [projects, setProjects] = useState([]);
  const [filterProjects, setFilterProjects] = useState([]);

  const navigate = useNavigate();

  const deleteProject = async (id) => {
    try {
      await projectApi.deleteProject(id);
      fetchProjects();
      message.success("Delete project successfully");
    } catch (error) {
      console.error(error);
    }
  };

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
      title: "Repository",
      dataIndex: "repository",
      key: "repository",
      render: (item) => {
        return (
          <Button type="link" onClick={() => window.open(item, '_blank')}>
            {item}
          </Button>
        );
      }
    },
    {
      title: "Created At",
      dataIndex: "createdAt",
      key: "createdAt",
    },
    {
      key: "action",
      render: (_, record) => {
        return (
          <div
            style={{
              display: "flex",
              gap: "2px",
            }}
          >
            <EditProject refresh={fetchProjects} project={record} />
            <Popconfirm
              title="Delete the project"
              description="Are you sure to delete this project?"
              okText="Yes"
              cancelText="No"
              onConfirm={() => deleteProject(record.id)}
            >
              <Button
                danger
                type="text"
                icon={<FaRegTrashCan />}
              />
            </Popconfirm>
          </div>
        );
      },
    },
  ];

  const fetchProjects = async () => {
    setLoading(true);
    try {
      const data = await projectApi.getProjects();
      setProjects(
        data.map((item, index) => {
          return {
            ...item,
            no: index + 1,
            key: item.id,
          };
        })
      );
      setFilterProjects(
        data.map((item, index) => {
          return {
            ...item,
            no: index + 1,
            key: item.id,
          };
        })
      );
    
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProjects();
  }, []);
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
        <CreateProject refresh={fetchProjects} />
        <Input.Search
          style={{ width: "300px" }}
          placeholder="Search"
          onSearch={(value) => setFilterProjects(projects.filter((project) => project.name.includes(value)))}
        />
      </div>
      <Table
        columns={columns}
        dataSource={filterProjects}
        pagination={true}
        loading={loading}
      />
    </div>
  );
}

export default ProjectsPage;
