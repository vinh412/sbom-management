import React, { useEffect, useState } from "react";
import { Col, Row } from "antd";
import BuildList from "./BuildList";
import OverviewCard from "./OverviewCard";
import PipelineTabs from "./PipelineTabs";
import buildApi from "../../api/build";
import { useParams } from "react-router-dom";
import pipelineApi from "../../api/pipeline";

function PipelinePage() {
  const [size, setSize] = useState(10);
  const [components, setComponents] = useState([]);
  const [builds, setBuilds] = useState([]);
  const params = useParams();
  const projectName = params.projectName;
  const pipelineName = params.pipelineName;
  useEffect(() => {
    const fetchBuilds = async () => {
      const searchParams = new URLSearchParams({
        search: "",
        page: "1",
        size,
        sortBy: "createdAt",
        order: "desc",
      });
      const data = await buildApi.getBuilds(
        projectName,
        pipelineName,
        searchParams
      );
      setBuilds(data.content);
    };
    fetchBuilds();
  }, [size]);

  useEffect(() => {
    const fetchComponents = async () => {
      const data = await pipelineApi.getLatestBuildComponents(
        projectName,
        pipelineName
      );
      setComponents(
        data.map((item, index) => {
          return { ...item, no: index + 1 };
        })
      );
    };
    fetchComponents();
  }, []);
  return (
    <div>
      <Row gutter={8} style={{ marginBottom: "8px" }}>
        <Col span={6}>
          <OverviewCard title="Builds" value={builds.length} />
        </Col>
        <Col span={6}>
          <OverviewCard title="Components" value={components.length} />
        </Col>
        <Col span={6}>
          <OverviewCard title="Vulnerabilities" value="2" />
        </Col>
        <Col span={6}>
          <OverviewCard title="Licenses" value="2" />
        </Col>
      </Row>
      <Row gutter={8}>
        <Col span={4}>
          <BuildList builds={builds} />
        </Col>
        <Col span={20}>
          <PipelineTabs components={components} />
        </Col>
      </Row>
    </div>
  );
}

export default PipelinePage;
