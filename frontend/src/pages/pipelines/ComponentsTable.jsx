import { Table, theme, Tooltip, Typography } from "antd";
import React from "react";
import { Link } from "react-router-dom";
import { FaBug, FaCheck  } from "react-icons/fa6";
import { IoIosWarning } from "react-icons/io";

function ComponentsTable({ dataSource }) {
  const {
    token: { red, yellow, orange, blue },
  } = theme.useToken();
  const columns = [
    {
      title: "No",
      dataIndex: "no",
      key: "no",
    },
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Version",
      key: "version",
      dataIndex: "version",
      render: (value, record) => {
        return (
          <span
            style={{
              display: "inline-flex",
              alignItems: "center",
              justifyContent: "space-between",
              minWidth: "100px",  
            }}
          >
            <Typography.Text style={{ marginRight: "2px" }}>
              {value}
            </Typography.Text>
            {!record.isLatest ? (
              <Tooltip
                title={`Outdated component. Latest version is ${record.latestVersion}`}
              >
                <IoIosWarning color={yellow} />
              </Tooltip>
            ) : <FaCheck color="green" />}
          </span>
        );
      },
    },
    {
      title: "Group",
      dataIndex: "groupName",
      key: "groupName",
    },
    {
      title: "Publisher",
      dataIndex: "publisher",
      key: "publisher",
      render: (value) => {
        return value ? value : "-";
      },
    },
    {
      title: "License",
      key: "licenses",
      dataIndex: "licenses",
      render: (value) => {
        return value.map((item) => (
          <div>
            <span style={{ display: "inline-flex", alignItems: "center", justifyContent: "space-between"}}>
              <Link
                style={{ display: "block", marginRight: "2px" }}
                to={`/licenses/${item.licenseId}`}
              >
                {item.licenseId}
              </Link>
              {item.isDeprecated && (
                <Tooltip title={`This license is deprecated.`}>
                  <IoIosWarning color={yellow} />
                </Tooltip>
              )}
            </span>
          </div>
        ));
      },
    },
    {
      title: "Vulnerabilities",
      key: "vulnerabilities",
      dataIndex: "vulnerabilities",
      render: (value) => {
        return value.map((item) => {
          const bugColor =
            item.cvssScore >= 9
              ? red
              : item.cvssScore >= 7
              ? orange
              : item.cvssScore >= 4
              ? yellow
              : blue;
          return (
            <span style={{ display: "inline-flex", alignItems: "center"}}>
              <Link style={{marginRight: "2px"}} to={`/vulnerabilities/${item}`}>{item.vulId}</Link>
              <Tooltip title={`CVSS Score: ${item.cvssScore}`}>
                <FaBug color={bugColor} />
              </Tooltip>
            </span>
          );
        });
      },
    },
  ];
  return (
    <div>
      <Table columns={columns} dataSource={dataSource} bordered />
    </div>
  );
}

export default ComponentsTable;
