import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import pipelineApi from "../../api/pipeline";
import { Button, Input, Pagination, Popconfirm, Table } from "antd";
import { FaPlus, FaRegTrashCan } from "react-icons/fa6";
import CreatePipeline from "../pipelines/CreatePipeline";

function ProjectDetail() {
  const [loading, setLoading] = useState(false);
  const [pipelines, setPipelines] = useState([]);

  const params = useParams();
  const projectName = params.projectName;

  const navigate = useNavigate();

  const columns = [
    {
      title: "No",
      dataIndex: "no",
      key: "no",
    },
    {
      title: "Pipeline Name",
      dataIndex: "name",
      key: "name",
      render: (item, record) => {
        return (
          <Button type="link" onClick={() => navigate(`${record.name}`)}>
            {item}
          </Button>
        );
      },
    },
    {
      title: "Branch",
      dataIndex: "branch",
      key: "branch",
      render: (item, record) => {
        return (
          <Button
            type="link"
            onClick={() => window.open(record.branchUrl, "_blank")}
          >
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
            <Popconfirm
              title="Delete the pipeline"
              description="Are you sure to delete this pipeline?"
              okText="Yes"
              cancelText="No"
              // onConfirm={() => }
            >
              <Button danger type="text" icon={<FaRegTrashCan />} />
            </Popconfirm>
          </div>
        );
      },
    },
  ];

  const deletePipeline = async (id) => {
    try {
      await pipelineApi.deletePipeline(id);
      fetchPipelines();
    } catch (error) {
      console.error(error);
    }
  };

  const fetchPipelines = async () => {
    setLoading(true);
    try {
      const data = await pipelineApi.getPipelinesByProjectName(projectName);
      setPipelines(
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
    fetchPipelines();
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
        <CreatePipeline refresh={fetchPipelines} />
        <Input.Search
          style={{ width: "300px" }}
          placeholder="Search"
          // onSearch={(value) => setSearch(value)}
        />
      </div>
      <Table columns={columns} dataSource={pipelines} pagination={true} />
    </div>
  );
}

export default ProjectDetail;
