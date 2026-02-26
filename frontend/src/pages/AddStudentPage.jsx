import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import api from '../api/axios';

/**
 * AddStudentPage Component — Form to add a new student.
 * 
 * NOTE: This page is ADMIN-only (protected by AdminRoute in App.jsx)
 * 
 * Features:
 *  - Form with: Name, Email, Course, Department fields
 *  - Validates all fields before submitting
 *  - Calls POST /api/students
 *  - Shows success/error message
 *  - "Go to Student List" button after success
 */
function AddStudentPage() {
    // ─── State ───────────────────────────────────────────────────────────────────
    const [form, setForm] = useState({
        name: '',
        email: '',
        course: '',
        department: '',
    });
    const [errors, setErrors] = useState({});      // Field-level validation errors
    const [apiError, setApiError] = useState('');  // API error message
    const [success, setSuccess] = useState(false); // Was the student added?
    const [loading, setLoading] = useState(false); // Submitting?
    const [addedStudent, setAddedStudent] = useState(null); // The added student

    const navigate = useNavigate();

    // ─── Available Courses & Departments ─────────────────────────────────────────
    const courses = ['B.Tech', 'M.Tech', 'BCA','B.E', 'MCA', 'B.Sc', 'M.Sc', 'MBA', 'B.Com', 'B.A'];
    const departments = [
        'Computer Science',
        'Information Technology',
        'Mechanical Engineering',
        'Civil Engineering',
        'Electronics',
        'Mathematics',
        'Physics',
        'Chemistry',
        'Business Administration',
    ];

    // ─── Input Change Handler ─────────────────────────────────────────────────────
    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
        // Clear the error for this field as user types
        if (errors[name]) {
            setErrors({ ...errors, [name]: '' });
        }
    };

    // ─── Form Validation ──────────────────────────────────────────────────────────
    const validate = () => {
        const newErrors = {};

        if (!form.name.trim()) {
            newErrors.name = 'Student name is required.';
        } else if (form.name.trim().length < 2) {
            newErrors.name = 'Name must be at least 2 characters.';
        }

        if (!form.email.trim()) {
            newErrors.email = 'Email is required.';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
            newErrors.email = 'Please enter a valid email address.';
        }

        if (!form.course) {
            newErrors.course = 'Please select a course.';
        }

        if (!form.department) {
            newErrors.department = 'Please select a department.';
        }

        return newErrors;
    };

    // ─── Submit Handler ───────────────────────────────────────────────────────────
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate first
        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setLoading(true);
        setApiError('');

        try {
            // POST to /api/students
            const response = await api.post('/students', form);
            setAddedStudent(response.data); // Store the created student
            setSuccess(true);               // Show success message
            // Reset form
            setForm({ name: '', email: '', course: '', department: '' });
        } catch (err) {
            if (err.response && err.response.status === 400) {
                setApiError('Failed to add student. Email might already be in use.');
            } else {
                setApiError('Cannot connect to server. Make sure the backend is running.');
            }
        } finally {
            setLoading(false);
        }
    };

    // ─── Reset to add another student ────────────────────────────────────────────
    const addAnother = () => {
        setSuccess(false);
        setAddedStudent(null);
        setForm({ name: '', email: '', course: '', department: '' });
        setErrors({});
        setApiError('');
    };

    // ─── Render ──────────────────────────────────────────────────────────────────
    return (
        <div className="page-wrapper">
            <Navbar />

            <main className="main-content">
                {/* Page Header */}
                <div className="page-header">
                    <div>
                        <h1 className="page-title">➕ Add New Student</h1>
                        <p className="page-subtitle">Fill in the details to register a new student</p>
                    </div>
                    <button
                        className="btn btn-secondary"
                        onClick={() => navigate('/students')}
                    >
                        ← Back to Students
                    </button>
                </div>

                {/* Success State */}
                {success ? (
                    <div className="success-card">
                        <div className="success-icon">🎉</div>
                        <h2 className="success-title">Student Added Successfully!</h2>
                        {addedStudent && (
                            <div className="success-details">
                                <div className="detail-row">
                                    <span className="detail-label">Name:</span>
                                    <span className="detail-value">{addedStudent.name}</span>
                                </div>
                                <div className="detail-row">
                                    <span className="detail-label">Email:</span>
                                    <span className="detail-value">{addedStudent.email}</span>
                                </div>
                                <div className="detail-row">
                                    <span className="detail-label">Course:</span>
                                    <span className="detail-value">{addedStudent.course}</span>
                                </div>
                                <div className="detail-row">
                                    <span className="detail-label">Department:</span>
                                    <span className="detail-value">{addedStudent.department}</span>
                                </div>
                                <div className="detail-row">
                                    <span className="detail-label">ID:</span>
                                    <span className="detail-value">#{addedStudent.id}</span>
                                </div>
                            </div>
                        )}
                        <div className="success-actions">
                            <button
                                className="btn btn-primary"
                                onClick={addAnother}
                                id="btn-add-another"
                            >
                                ➕ Add Another Student
                            </button>
                            <button
                                className="btn btn-secondary"
                                onClick={() => navigate('/students')}
                                id="btn-view-students-after-add"
                            >
                                📋 View All Students
                            </button>
                        </div>
                    </div>
                ) : (
                    /* Add Student Form */
                    <div className="form-card">
                        {/* API Error */}
                        {apiError && (
                            <div className="alert alert-error">⚠️ {apiError}</div>
                        )}

                        <form onSubmit={handleSubmit} id="add-student-form">
                            <div className="form-grid">

                                {/* Name Field */}
                                <div className="form-group">
                                    <label className="form-label" htmlFor="name">
                                        Full Name <span className="required">*</span>
                                    </label>
                                    <input
                                        id="name"
                                        type="text"
                                        name="name"
                                        className={`form-input ${errors.name ? 'input-error' : ''}`}
                                        placeholder="e.g., Alice Johnson"
                                        value={form.name}
                                        onChange={handleChange}
                                    />
                                    {errors.name && <span className="field-error">{errors.name}</span>}
                                </div>

                                {/* Email Field */}
                                <div className="form-group">
                                    <label className="form-label" htmlFor="email">
                                        Email Address <span className="required">*</span>
                                    </label>
                                    <input
                                        id="email"
                                        type="email"
                                        name="email"
                                        className={`form-input ${errors.email ? 'input-error' : ''}`}
                                        placeholder="e.g., alice@college.edu"
                                        value={form.email}
                                        onChange={handleChange}
                                    />
                                    {errors.email && <span className="field-error">{errors.email}</span>}
                                </div>

                                {/* Course Field */}
                                <div className="form-group">
                                    <label className="form-label" htmlFor="course">
                                        Course <span className="required">*</span>
                                    </label>
                                    <select
                                        id="course"
                                        name="course"
                                        className={`form-input form-select ${errors.course ? 'input-error' : ''}`}
                                        value={form.course}
                                        onChange={handleChange}
                                    >
                                        <option value="">-- Select Course --</option>
                                        {courses.map((c) => (
                                            <option key={c} value={c}>{c}</option>
                                        ))}
                                    </select>
                                    {errors.course && <span className="field-error">{errors.course}</span>}
                                </div>

                                {/* Department Field */}
                                <div className="form-group">
                                    <label className="form-label" htmlFor="department">
                                        Department <span className="required">*</span>
                                    </label>
                                    <select
                                        id="department"
                                        name="department"
                                        className={`form-input form-select ${errors.department ? 'input-error' : ''}`}
                                        value={form.department}
                                        onChange={handleChange}
                                    >
                                        <option value="">-- Select Department --</option>
                                        {departments.map((d) => (
                                            <option key={d} value={d}>{d}</option>
                                        ))}
                                    </select>
                                    {errors.department && <span className="field-error">{errors.department}</span>}
                                </div>
                            </div>

                            {/* Submit Button */}
                            <div className="form-footer">
                                <button
                                    type="button"
                                    className="btn btn-secondary"
                                    onClick={() => navigate('/students')}
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    className="btn btn-primary"
                                    disabled={loading}
                                    id="btn-submit-student"
                                >
                                    {loading ? '⏳ Adding...' : '➕ Add Student'}
                                </button>
                            </div>
                        </form>
                    </div>
                )}
            </main>
        </div>
    );
}

export default AddStudentPage;
