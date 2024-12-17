import React, { useEffect } from "react";
import {
  Breadcrumb,
  Button,
  Dropdown,
  Layout,
  Menu,
  Switch,
  theme,
  Typography,
} from "antd";
import {
  ApartmentOutlined,
  MoonOutlined,
  ProjectOutlined,
  SunOutlined,
} from "@ant-design/icons";
import {
  FaShieldHalved,
  FaScaleBalanced,
  FaRegCircleUser,
  FaArrowRightFromBracket,
} from "react-icons/fa6";
import { Link, NavLink, Outlet, useLocation, useNavigate } from "react-router-dom";
import BuildList from "./pages/pipelines/BuildList";
const { Header, Content, Footer, Sider } = Layout;

const sidebarItems = [
  {
    key: "0",
    label: "Dashboard",
    type: "group",
    children: [
      {
        key: "1",
        icon: <ProjectOutlined />,
        label: <NavLink to="/projects">Projects</NavLink>,
      },
      {
        key: "2",
        icon: <ApartmentOutlined />,
        label: <NavLink to="/components">Components</NavLink>,
      },
      {
        key: "3",
        icon: <FaShieldHalved />,
        label: <NavLink to="/vulnerabilities">Vulnerabilities</NavLink>,
      },
      {
        key: "4",
        icon: <FaScaleBalanced />,
        label: <NavLink to="/licenses">Licenses</NavLink>,
      },
    ],
  },
  {
    key: "5",
    label: "Administration",
    type: "group",
    children: [
      {
        key: "6",
        label: <Link to="/admin/users">User Management</Link>,
      },
      {
        key: "7",
        label: <Link to="/admin/users">Role</Link>,
      },
      {
        key: "8",
        label: <Link to="/permissions">Permission</Link>,
      },
    ],
  },
];

const handleMenuClick = (e) => {
  if (e.key === "logout") {
    localStorage.removeItem("jwt");
    window.location.href = "/login";
  }
};

const dropdownItem = [
  {
    label: "Logout",
    icon: <FaArrowRightFromBracket />,
    key: "logout",
  },
];
const dropdownMenu = {
  items: dropdownItem,
  onClick: handleMenuClick,
};

const MyLayout = ({ setDarkMode }) => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const navigate = useNavigate();
  const location = useLocation();
  const isPipelinePage = /^\/projects\/[^\/]+\/[^\/]+$/.test(location.pathname);
  const breadCrumdItems = location.pathname.split("/").reduce((acc, item, index) => {
    if (index === 0) {
      return acc;
    }
    return [...acc, { title: item }];
  });

  const checkJwtValidity = () => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      return false;
    }
    return true;
  };

  useEffect(() => {
    if (!checkJwtValidity()) {
      navigate("/login");
    }
  }, [navigate]);

  return (
    <Layout>
      <Header
        style={{
          background: colorBgContainer,
          position: "sticky",
          top: 0,
          zIndex: 1,
          width: "100%",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <Typography.Title level={2} style={{ margin: 0 }}>
          SBOM Management
        </Typography.Title>
        <div
          style={{
            display: "flex",
            gap: "8px",
            alignItems: "center",
          }}
        >
          <Switch
            checkedChildren={<MoonOutlined />}
            unCheckedChildren={<SunOutlined />}
            onChange={(checked) => setDarkMode(checked)}
          />
          <Dropdown trigger={["click"]} menu={dropdownMenu}>
            <Button type="text" icon={<FaRegCircleUser size={24} />} />
          </Dropdown>
        </div>
      </Header>
      <Layout>
        <Sider
          width={200}
          style={{
            background: colorBgContainer,
            position: "fixed",
            height: "100vh",
            overflow: "hidden",
          }}
        >
          <Menu
            mode="inline"
            defaultSelectedKeys={["1"]}
            style={{
              borderRight: 0,
            }}
            items={sidebarItems}
          />
        </Sider>
        <Layout
          style={{
            marginLeft: "200px",
            minHeight: "100vh",
          }}
        >
          <Breadcrumb
            separator=">"
            items={breadCrumdItems}
            style={{
              margin: "16px 0 16px 16px",
            }}
          />
          <Content
            style={{
              padding: "0 48px",
            }}
          >
            <Outlet />
          </Content>
          <Footer
            style={{
              textAlign: "center",
            }}
          >
            ©{new Date().getFullYear()} Made by vinhdd
          </Footer>
        </Layout>
      </Layout>
    </Layout>
  );
};
export default MyLayout;
