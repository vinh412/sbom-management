import { ConfigProvider, theme } from "antd";
import MyLayout from "./MyLayout";
import { useState } from "react";
import { BrowserRouter as Router, Route, Routes, createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./pages/login/Login";
import ProjectsPage from "./pages/projects/ProjectsPage";
import ComponentsPage from "./pages/components/ComponentsPage";
import VulnerabilitiesPage from "./pages/vulnerabilities/VulnerabilitiesPage";
import LicensesPage from "./pages/licenses/LicensesPage";
import ProjectDetail from "./pages/projects/ProjectDetail";
import LicenseDetail, { loader as licenseDetailLoader } from "./pages/licenses/LicenseDetail";
import UserDetail, { loader as userDetailLoader } from "./pages/user-management/UserDetail";
import ErrorPage from "./pages/errors/ErrorPage";
import UserManagementPage from "./pages/user-management/UserManagementPage";
import PermissionsPage from "./pages/permission/PermissionsPage";
import PipelinePage from "./pages/pipelines/PipelinePage";


function App() {
  const [isDarkMode, setDarkMode] = useState(false);
  const route = createBrowserRouter([
    {
      path: "/",
      element: <MyLayout setDarkMode={setDarkMode} />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: "projects",
          element: <ProjectsPage />,
        },
        {
          path: "projects/:projectName",
          element: <ProjectDetail />,
        },
        {
          path: "projects/:projectName/:pipelineName",
          element: <PipelinePage />
        },
        {
          path: "components",
          element: <ComponentsPage />
        },
        {
          path: "vulnerabilities",
          element: <VulnerabilitiesPage />
        },
        {
          path: "licenses",
          element: <LicensesPage />,
        },
        {
          path: "/licenses/:id",
          element: <LicenseDetail />,
          loader: licenseDetailLoader
        },
        {
          path: "/admin/users",
          element: <UserManagementPage />
        },
        {
          path: "/admin/users/:username",
          element: <UserDetail />,
          loader: userDetailLoader
        },
        {
          path: "/permissions",
          element: <PermissionsPage />
        }
      ]
    },
    {
      path: "/login",
      element: <Login />
    },
    {
      path: "/test",
      element: <ComponentsPage />
    }
  ])
  
  return (
    <ConfigProvider
      theme={{
        algorithm: isDarkMode ? theme.darkAlgorithm : theme.defaultAlgorithm,
        token: {
          colorPrimary: "#00B96B",
        },
      }}
    >
      {/* <Router>
        <Routes>
          <Route
            path="/"
            element={
              <MyLayout isDarkMode={isDarkMode} setDarkMode={setDarkMode} />
            }
          >
            <Route path="projects" element={<ProjectsPage />} />
            <Route path="projects/:projectId" element={<ProjectDetail />} />

            <Route path="components" element={<ComponentsPage />} />
            <Route path="vulnerabilities" element={<VulnerabilitiesPage />} />
            <Route path="licenses" element={<LicensesPage />} />
            <Route path="licenses/:id" element={<LicenseDetail />} loader={licenseLoader}/>
          </Route>

          <Route path="/login" element={<Login />} />
        </Routes>
      </Router> */}
      <RouterProvider router={route}/>
    </ConfigProvider>
  );
}

export default App;
