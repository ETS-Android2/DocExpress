import EditIcon from "@mui/icons-material/Edit";
import SaveAsIcon from "@mui/icons-material/SaveAs";
import ShareLocationIcon from "@mui/icons-material/ShareLocation";
import MoveToInboxIcon from "@mui/icons-material/MoveToInbox";
import PieChartOutlineIcon from "@mui/icons-material/PieChartOutline";
import CommentsDisabledIcon from "@mui/icons-material/CommentsDisabled";
import PresentToAllIcon from "@mui/icons-material/PresentToAll";

const dashboardRoutes = [
  {
    path: "/new-application",
    name: "Start New Application",
    icon: EditIcon,
    layout: "/admin",
  },
  {
    path: "/view-sent-applications",
    name: "Sent Applications",
    icon: PresentToAllIcon,
    layout: "/admin",
  },
  {
    path: "/dashboard",
    name: "Draft Applications",
    icon: SaveAsIcon,
    layout: "/admin",
  },
  {
    path: "/track-application",
    name: "Track Application",
    icon: ShareLocationIcon,
    layout: "/admin",
  },
  {
    path: "/view-received-applications",
    name: "Received Applications",
    icon: MoveToInboxIcon,
    layout: "/admin",
  },
  {
    path: "/view-closed-applications",
    name: "Closed Applications",
    icon: CommentsDisabledIcon,
    layout: "/admin",
  },
  {
    path: "/dashboard",
    name: "Summary Report",
    icon: PieChartOutlineIcon,
    layout: "/admin",
  },
];

export default dashboardRoutes;
