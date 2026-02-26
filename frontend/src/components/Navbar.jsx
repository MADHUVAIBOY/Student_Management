import { Link, useNavigate, useLocation } from 'react-router-dom';

/**
 * Navbar Component — Top navigation bar shown on all protected pages.
 * 
 * Features:
 *  - Shows navigation links (Dashboard, Students)
 *  - Shows "Add Student" link only for ADMIN users
 *  - Displays logged-in username and role
 *  - Logout button clears localStorage and redirects to login
 */
function Navbar() {
    const navigate = useNavigate();
    const location = useLocation();

    // Read user info from localStorage
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');

    // ─── Logout Handler ──────────────────────────────────────────────────────────
    const handleLogout = () => {
        // Clear all stored user data
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        // Redirect to login page
        navigate('/');
    };

    // Helper to check if a nav link is active
    const isActive = (path) => location.pathname === path;

    return (
        <nav className="navbar">
            {/* Brand / Logo */}
            <div className="navbar-brand">
                <span className="navbar-logo">🎓</span>
                <span className="navbar-title">StudentMS</span>
            </div>

            {/* Navigation Links */}
            <ul className="navbar-links">
                <li>
                    <Link
                        to="/dashboard"
                        className={`nav-link ${isActive('/dashboard') ? 'active' : ''}`}
                    >
                        📊 Dashboard
                    </Link>
                </li>
                <li>
                    <Link
                        to="/students"
                        className={`nav-link ${isActive('/students') ? 'active' : ''}`}
                    >
                        👥 Students
                    </Link>
                </li>
                {/* Users Management — ADMIN only */}
                {role === 'ADMIN' && (
                    <li>
                        <Link
                            to="/users"
                            className={`nav-link ${isActive('/users') ? 'active' : ''}`}
                        >
                            🔑 Users
                        </Link>
                    </li>
                )}
            </ul>

            {/* User Info & Logout */}
            <div className="navbar-user">
                <div className="user-info">
                    <span className="user-avatar">
                        {username ? username.charAt(0).toUpperCase() : 'U'}
                    </span>
                    <div className="user-details">
                        <span className="user-name">{username}</span>
                        <span className={`user-role ${role === 'ADMIN' ? 'role-admin' : 'role-user'}`}>
                            {role}
                        </span>
                    </div>
                </div>
                <button className="logout-btn" onClick={handleLogout}>
                    🚪 Logout
                </button>
            </div>
        </nav>
    );
}

export default Navbar;
