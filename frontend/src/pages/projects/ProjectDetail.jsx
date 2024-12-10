import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import pipelineApi from '../../api/pipeline';
import { Button, Input, Pagination, Table } from 'antd';
import { FaPlus } from 'react-icons/fa6';

function ProjectDetail() {
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [sortBy, setSortBy] = useState("createdAt");
  const [order, setOrder] = useState("desc");
  const [pipelines, setPipelines] = useState([]);
  const [totalElements, setTotalElements] = useState(0);

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
        return <Button type="link" onClick={() => navigate(`${record.name}`)}>{item}</Button>;
      }
    },
    {
      title: "Description",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Created At",
      dataIndex: "createdAt",
      key: "createdAt"
    }
  ];

  useEffect(() => {
    const fetchPipelines = async () => {
      setLoading(true);
      const searchParams = new URLSearchParams({
        search,
        page,
        size,
        sortBy,
        order,
      });
      try {
        const data = await pipelineApi.getPipelinesByProjectName(projectName, searchParams);
        setPipelines(data.content.map((item, index) => {
          return {
            ...item,
            no: index + 1 + ((page - 1) * size),
          };
        }));
        setTotalElements(data.page.totalElements);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
    fetchPipelines();
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
      <Table columns={columns} dataSource={pipelines} pagination={false} />
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

export default ProjectDetail