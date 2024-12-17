import { theme, Typography } from "antd";
import React from "react";
import { Link } from "react-router-dom";
import { FaRegCircleCheck, FaRegCircleXmark } from "react-icons/fa6";

const convertDuration = (duration) => {
  return (duration / 1000).toFixed(1);
}

function BuildList({ builds }) {
  const {
    token: { borderRadius, padding, paddingSM, colorBgContainer },
  } = theme.useToken();
  return (
    <div
      style={{
        borderRadius: borderRadius,
        paddingTop: padding,
        paddingBottom: padding,
        background: colorBgContainer,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Typography.Title level={4} style={{ margin: 0 }}>
        Builds
      </Typography.Title>
      {builds.map((build, index) => (
        <BuildItem key={build.number} build={build} index={index} />
      ))}
      <Link>More</Link>
    </div>
  );
}

function BuildItem({ build: { number, result, duration, startAt }, index }) {
  const {
    token: { borderRadiusSM, paddingXS, colorBorder },
  } = theme.useToken();
  console.log(index);
  const bgColor = index % 2 === 0 ? colorBorder : "transparent";
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        gap: "4px",
        padding: paddingXS,
      }}
    >
      <div style={{marginTop: "2px"}}>
        {result === "SUCCESS" ? (
          <FaRegCircleCheck color="green" />
        ) : (
          <FaRegCircleXmark color="red" />
        )}
      </div>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
        }}
      >
        <div style={{display: 'flex', justifyContent: 'space-between'}}>
        <Link type="link">#{number}</Link>
        <Typography.Text type="secondary">{convertDuration(duration)} s</Typography.Text>

        </div>
        <Typography.Text>{startAt}</Typography.Text>
      </div>
    </div>
  );
}

export default BuildList;
