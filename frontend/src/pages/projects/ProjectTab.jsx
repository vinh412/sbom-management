import React from "react";
import { Tabs, theme } from "antd";
import ProjectDetail from "./ProjectDetail";
const onChange = (key) => {
  console.log(key);
};

function ProjectTab({ components, dependencies, builds }) {
  const items = [
    {
      key: "1",
      label: "Pipelines",
      children: <ProjectDetail />,
    },
    {
      key: "2",
      label: "Users",
      children: <div></div>,
    },
  ];
  const {
    token: { borderRadius, padding, paddingSM, colorBgContainer },
  } = theme.useToken();
  return (
    <div style={{
      background: colorBgContainer,
      padding: padding,
      borderRadius: borderRadius,
    }}>
      <Tabs defaultActiveKey="1" items={items} onChange={onChange} />
    </div>
  ) 
}
export default ProjectTab;
