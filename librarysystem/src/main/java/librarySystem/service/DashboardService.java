package librarySystem.service;

import librarySystem.Model.DashboardStats;
import librarySystem.DAO.DashboardStatsDAO;

public class DashboardService {

    private final DashboardStatsDAO dashboardDao;

    public DashboardService() {
        this.dashboardDao = new DashboardStatsDAO();
    }

  
    public DashboardStats fetchDashboardStats() {
        try {
            return dashboardDao.getDashboardStats();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching dashboard statistics: " + e.getMessage(), e);
        }
    }
}