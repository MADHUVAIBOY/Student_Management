import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import api from '../api/axios';

/**
 * DashboardPage Component — Home screen after login.
 * 
 * Displays:
 *  - Welcome message with the logged-in username
 *  - Total student count (fetched from API)
 *  - Role badge (ADMIN / USER)
 *  - Quick action cards
 */
function DashboardPage() {
    // ─── State ───────────────────────────────────────────────────────────────────
    const [totalStudents, setTotalStudents] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const navigate = useNavigate();

    // Read user info from localStorage
    const username = localStorage.getItem('username') || 'User';
    const role = localStorage.getItem('role') || 'USER';

    // ─── Fetch Student Count on Mount ────────────────────────────────────────────
    useEffect(() => {
        const fetchCount = async () => {
            try {
                const response = await api.get('/students/count');
                setTotalStudents(response.data.total);
            } catch (err) {
                setError('Could not fetch student count.');
                console.error('Error fetching count:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchCount();
    }, []); // Empty array = run once when component mounts

    // ─── Render ──────────────────────────────────────────────────────────────────
    return (
        <div className="page-wrapper">
            <Navbar />

            <main className="main-content">
                {/* Welcome Header */}
                <div className="dashboard-header">
                    <div className="welcome-section">
                        <div className="welcome-avatar">
                            {username.charAt(0).toUpperCase()}
                        </div>
                        <div>
                            <h1 className="welcome-title">
                                Welcome back, <span className="highlight">{username}</span>! 👋
                            </h1>
                            <p className="welcome-subtitle">
                                Here's what's happening in your system today.
                            </p>
                        </div>
                    </div>
                    <span className={`role-badge ${role === 'ADMIN' ? 'badge-admin' : 'badge-user'}`}>
                        {role === 'ADMIN' ? '👑 Administrator' : '👤 User'}
                    </span>
                </div>

                {/* Error alert */}
                {error && <div className="alert alert-error">⚠️ {error}</div>}

                {/* Stats Cards */}
                <div className="stats-grid">
                    {/* Total Students Card */}
                    <div className="stat-card stat-card-blue">
                        <div className="stat-icon">👥</div>
                        <div className="stat-info">
                            <span className="stat-label">Total Students</span>
                            <span className="stat-value">
                                {loading ? (
                                    <span className="loading-dots">···</span>
                                ) : (
                                    totalStudents
                                )}
                            </span>
                        </div>
                    </div>

                    {/* Role Card */}
                    <div className="stat-card stat-card-purple">
                        <div className="stat-icon">🔐</div>
                        <div className="stat-info">
                            <span className="stat-label">Your Role</span>
                            <span className="stat-value role-value">{role}</span>
                        </div>
                    </div>

                    {/* System Card */}
                    <div className="stat-card stat-card-green">
                        <div className="stat-icon">✅</div>
                        <div className="stat-info">
                            <span className="stat-label">System Status</span>
                            <span className="stat-value status-online">Online</span>
                        </div>
                    </div>
                </div>

                {/* Quick Actions */}
                <div className="quick-actions-section">
                    <h2 className="section-title">Quick Actions</h2>
                    <div className="quick-actions-grid">

                        {/* View Students */}
                        <div
                            className="action-card"
                            onClick={() => navigate('/students')}
                            role="button"
                            tabIndex={0}
                            id="btn-view-students"
                        >
                            <div className="action-icon">📋</div>
                            <h3 className="action-title">View Students</h3>
                            <p className="action-desc">Browse and search the student list</p>
                            <span className="action-arrow">→</span>
                        </div>

                        {/* Add Student (ADMIN only) */}
                        {role === 'ADMIN' && (
                            <div
                                className="action-card action-card-special"
                                onClick={() => navigate('/add-student')}
                                role="button"
                                tabIndex={0}
                                id="btn-add-student-dashboard"
                            >
                                <div className="action-icon">➕</div>
                                <h3 className="action-title">Add Student</h3>
                                <p className="action-desc">Register a new student record</p>
                                <span className="action-arrow">→</span>
                            </div>
                        )}

                        {/* Info Card for non-admin */}
                        {role !== 'ADMIN' && (
                            <div className="action-card action-card-info">
                                <div className="action-icon">ℹ️</div>
                                <h3 className="action-title">Read-Only Access</h3>
                                <p className="action-desc">
                                    You can view and search students. Contact an Admin to add, edit, or delete.
                                </p>
                            </div>
                        )}
                    </div>
                </div>

                {/* System Info */}
                <div className="system-info-card">
                    <h3 className="system-info-title">ℹ️ System Information</h3>
                    <div className="system-info-grid">
                        <div className="info-item">
                            <span className="info-label">Backend</span>
                            <span className="info-value">Spring Boot 3.2</span>
                        </div>
                        <div className="info-item">
                            <span className="info-label">Database</span>
                            <span className="info-value">MySQL 8</span>
                        </div>
                        <div className="info-item">
                            <span className="info-label">Frontend</span>
                            <span className="info-value">React 18 + Vite</span>
                        </div>
                        <div className="info-item">
                            <span className="info-label">API URL</span>
                            <span className="info-value">http://localhost:8080/api</span>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
}

export default DashboardPage;
