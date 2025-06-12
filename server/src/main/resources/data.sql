-- Clear existing data if needed (optional)
DELETE FROM lecture_files;
DELETE FROM bookings;
DELETE FROM lectures;
DELETE FROM missions;
DELETE FROM admins;
DELETE FROM freelancers;
DELETE FROM interns;
DELETE FROM users;
DELETE FROM rooms;

-- Insert 20 Rooms of various types
INSERT INTO rooms (type, size_limit, price_per_hour) VALUES
                                                         ('CAFE_ROOM', 8, 7.50), ('CAFE_ROOM', 6, 8.50), ('CAFE_ROOM', 4, 9.00),
                                                         ('OPEN_ROOM', 10, 5.00), ('OPEN_ROOM', 12, 6.00), ('OPEN_ROOM', 8, 4.50),
                                                         ('MEETING_ROOM', 5, 15.00), ('MEETING_ROOM', 8, 18.00), ('MEETING_ROOM', 10, 20.00),
                                                         ('PRIVATE_OFFICE', 3, 25.00), ('PRIVATE_OFFICE', 2, 30.00), ('PRIVATE_OFFICE', 4, 22.00),
                                                         ('LOUNGE', 12, 10.00), ('LOUNGE', 15, 12.00), ('LOUNGE', 8, 8.00),
                                                         ('CONFERENCE_ROOM', 20, 30.00), ('CONFERENCE_ROOM', 25, 35.00), ('CONFERENCE_ROOM', 15, 25.00),
                                                         ('BRAINSTORMING_ROOM', 6, 12.00), ('QUIET_ZONE', 4, 8.00);

-- Insert 50 users with explicit IDs (15 admins, 20 freelancers, 15 interns)
INSERT INTO users (id, username, email, password, role, enabled, confirmation_token) VALUES
-- Admins (1-15)
(1, 'admin1', 'admin1@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(2, 'admin2', 'admin2@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(3, 'admin3', 'admin3@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(4, 'admin4', 'admin4@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(5, 'admin5', 'admin5@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(6, 'admin6', 'admin6@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(7, 'admin7', 'admin7@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(8, 'admin8', 'admin8@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(9, 'admin9', 'admin9@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(10, 'admin10', 'admin10@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(11, 'admin11', 'admin11@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(12, 'admin12', 'admin12@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(13, 'admin13', 'admin13@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(14, 'admin14', 'admin14@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),
(15, 'admin15', 'admin15@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'ADMIN', TRUE, NULL),

-- Freelancers (16-35)
(16, 'freelancer1', 'freelancer1@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(17, 'freelancer2', 'freelancer2@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(18, 'freelancer3', 'freelancer3@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(19, 'freelancer4', 'freelancer4@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(20, 'freelancer5', 'freelancer5@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(21, 'freelancer6', 'freelancer6@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(22, 'freelancer7', 'freelancer7@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(23, 'freelancer8', 'freelancer8@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(24, 'freelancer9', 'freelancer9@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(25, 'freelancer10', 'freelancer10@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(26, 'freelancer11', 'freelancer11@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(27, 'freelancer12', 'freelancer12@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(28, 'freelancer13', 'freelancer13@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(29, 'freelancer14', 'freelancer14@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(30, 'freelancer15', 'freelancer15@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', TRUE, NULL),
(31, 'freelancer16', 'freelancer16@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', FALSE, 'token123'),
(32, 'freelancer17', 'freelancer17@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', FALSE, 'token456'),
(33, 'freelancer18', 'freelancer18@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', FALSE, 'token789'),
(34, 'freelancer19', 'freelancer19@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', FALSE, 'token101'),
(35, 'freelancer20', 'freelancer20@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'FREELANCER', FALSE, 'token112'),

-- Interns (36-50)
(36, 'intern1', 'intern1@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(37, 'intern2', 'intern2@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(38, 'intern3', 'intern3@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(39, 'intern4', 'intern4@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(40, 'intern5', 'intern5@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(41, 'intern6', 'intern6@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(42, 'intern7', 'intern7@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(43, 'intern8', 'intern8@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(44, 'intern9', 'intern9@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(45, 'intern10', 'intern10@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', TRUE, NULL),
(46, 'intern11', 'intern11@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', FALSE, 'token131'),
(47, 'intern12', 'intern12@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', FALSE, 'token141'),
(48, 'intern13', 'intern13@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', FALSE, 'token151'),
(49, 'intern14', 'intern14@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', FALSE, 'token161'),
(50, 'intern15', 'intern15@example.com', '$2a$10$fdXQ1EIpjaOOGi/g5VYu/.hyml/x2EXBYXen4ba/zDZMcKsfCoCka', 'INTERN', FALSE, 'token171');

-- Insert into subclass tables using the same IDs
INSERT INTO admins (id) VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), (13), (14), (15);
INSERT INTO freelancers (id) VALUES
                                 (16), (17), (18), (19), (20), (21), (22), (23), (24), (25),
                                 (26), (27), (28), (29), (30), (31), (32), (33), (34), (35);
INSERT INTO interns (id) VALUES
                             (36), (37), (38), (39), (40), (41), (42), (43), (44), (45),
                             (46), (47), (48), (49), (50);

-- Reset auto-increment to avoid PK conflicts on future inserts
ALTER TABLE users ALTER COLUMN id RESTART WITH 51;
-- Note: The exact syntax for resetting auto-increment can vary by SQL dialect.
-- For PostgreSQL: ALTER SEQUENCE users_id_seq RESTART WITH 51;
-- For MySQL: ALTER TABLE users AUTO_INCREMENT = 51;
-- For SQL Server: DBCC CHECKIDENT ('users', RESEED, 50); (Next will be 51)
-- For H2 (used in Spring Boot default): ALTER TABLE users ALTER COLUMN id RESTART WITH 51; (This is fine)

-- Insert 50 Missions assigned to various freelancers
INSERT INTO missions (name, deadline, progress, freelancer_id, description) VALUES
                                                                                ('Website Redesign', '2025-06-01', 'IN_PROGRESS', 16, 'Complete overhaul of company website with modern design and improved user experience'),
                                                                                ('SEO Optimization', '2025-07-15', 'NOT_YET_STARTED', 16, 'Improve search engine rankings through keyword optimization and backlink building'),
                                                                                ('Backend Revamp', '2025-06-10', 'DONE', 17, 'Refactored server-side codebase for better performance and scalability'),
                                                                                ('Logo Design', '2025-06-05', 'IN_PROGRESS', 18, 'Create a fresh visual identity that represents our brand values'),
                                                                                ('Marketing Plan', '2025-08-01', 'NOT_YET_STARTED', 17, 'Develop comprehensive 12-month marketing strategy across all channels'),
                                                                                ('Mobile App Development', '2025-07-20', 'IN_PROGRESS', 19, 'Building iOS and Android apps with React Native for cross-platform efficiency'),
                                                                                ('Database Migration', '2025-06-15', 'DONE', 20, 'Successfully moved from MySQL to PostgreSQL with zero downtime'),
                                                                                ('UI/UX Overhaul', '2025-07-01', 'IN_PROGRESS', 21, 'Redesigning user interfaces based on latest usability research'),
                                                                                ('Content Strategy', '2025-08-15', 'NOT_YET_STARTED', 22, 'Plan and schedule engaging content for blog and social media'),
                                                                                ('E-commerce Setup', '2025-07-10', 'IN_PROGRESS', 23, 'Implementing WooCommerce platform with custom payment gateways'),
                                                                                ('Social Media Campaign', '2025-06-25', 'DONE', 24, 'Ran successful summer promotion across Facebook and Instagram'),
                                                                                ('Brand Identity', '2025-08-20', 'IN_PROGRESS', 25, 'Developing complete brand guidelines including colors, fonts, and tone'),
                                                                                ('API Integration', '2025-07-05', 'NOT_YET_STARTED', 26, 'Connect our platform to third-party services via REST APIs'),
                                                                                ('Data Analysis', '2025-06-30', 'DONE', 27, 'Processed customer behavior data to identify key trends'),
                                                                                ('Cloud Migration', '2025-08-10', 'IN_PROGRESS', 28, 'Moving infrastructure from on-premise servers to AWS'),
                                                                                ('Security Audit', '2025-07-25', 'NOT_YET_STARTED', 29, 'Comprehensive penetration testing and vulnerability assessment'),
                                                                                ('Payment System', '2025-06-20', 'DONE', 30, 'Integrated Stripe and PayPal with fraud detection features'),
                                                                                ('CRM Implementation', '2025-08-05', 'IN_PROGRESS', 16, 'Setting up HubSpot with custom pipelines and automation'),
                                                                                ('Analytics Dashboard', '2025-07-15', 'NOT_YET_STARTED', 17, 'Build custom data visualization tool for business metrics'),
                                                                                ('Email Marketing', '2025-06-12', 'DONE', 18, 'Created and sent monthly newsletter to 50,000 subscribers'),
                                                                                ('Video Production', '2025-08-18', 'IN_PROGRESS', 19, 'Producing explainer videos for product features'),
                                                                                ('Copywriting', '2025-07-22', 'NOT_YET_STARTED', 20, 'Write compelling product descriptions and landing page content'),
                                                                                ('Graphic Design', '2025-06-08', 'DONE', 21, 'Designed marketing materials for trade show booth'),
                                                                                ('Market Research', '2025-08-22', 'IN_PROGRESS', 22, 'Analyzing competitor offerings and customer preferences'),
                                                                                ('Product Photography', '2025-07-18', 'NOT_YET_STARTED', 23, 'Professional photoshoot for new product line'),
                                                                                ('Technical Documentation', '2025-06-28', 'DONE', 24, 'Wrote comprehensive API documentation for developers'),
                                                                                ('Training Materials', '2025-08-25', 'IN_PROGRESS', 25, 'Creating employee onboarding videos and manuals'),
                                                                                ('Customer Surveys', '2025-07-30', 'NOT_YET_STARTED', 26, 'Design survey to measure satisfaction and gather feedback'),
                                                                                ('Accessibility Audit', '2025-06-18', 'DONE', 27, 'Ensured website meets WCAG 2.1 AA standards'),
                                                                                ('Localization', '2025-08-28', 'IN_PROGRESS', 28, 'Adapting content and UI for Spanish and French markets'),
                                                                                ('Performance Optimization', '2025-07-08', 'NOT_YET_STARTED', 29, 'Improve page load speeds and reduce server response times'),
                                                                                ('Bug Fixing', '2025-06-22', 'DONE', 30, 'Resolved 47 critical issues from error logs'),
                                                                                ('Feature Development', '2025-08-15', 'IN_PROGRESS', 16, 'Building dark mode and offline capabilities'),
                                                                                ('User Testing', '2025-07-12', 'NOT_YET_STARTED', 17, 'Conduct usability studies with target audience'),
                                                                                ('Prototyping', '2025-06-14', 'DONE', 18, 'Created interactive Figma prototypes for client approval'),
                                                                                ('Wireframing', '2025-08-12', 'IN_PROGRESS', 19, 'Designing low-fidelity layouts for new application'),
                                                                                ('Competitor Analysis', '2025-07-17', 'NOT_YET_STARTED', 20, 'Research and document competitor strengths and weaknesses'),
                                                                                ('Lead Generation', '2025-06-24', 'DONE', 21, 'Implemented automated lead capture system'),
                                                                                ('Sales Funnel', '2025-08-17', 'IN_PROGRESS', 22, 'Optimizing conversion path from visitor to customer'),
                                                                                ('Customer Support', '2025-07-19', 'NOT_YET_STARTED', 23, 'Set up Zendesk with knowledge base and ticketing'),
                                                                                ('Community Building', '2025-06-26', 'DONE', 24, 'Launched and grew Discord server to 1,000 members'),
                                                                                ('Influencer Outreach', '2025-08-19', 'IN_PROGRESS', 25, 'Identifying and contacting relevant industry influencers'),
                                                                                ('Press Release', '2025-07-21', 'NOT_YET_STARTED', 26, 'Draft announcement for new funding round'),
                                                                                ('Event Planning', '2025-06-16', 'DONE', 27, 'Organized successful virtual product launch event'),
                                                                                ('Webinar Setup', '2025-08-21', 'IN_PROGRESS', 28, 'Preparing technical infrastructure for monthly webinars'),
                                                                                ('Podcast Production', '2025-07-23', 'NOT_YET_STARTED', 29, 'Launch company podcast with industry interviews'),
                                                                                ('Newsletter Creation', '2025-06-19', 'DONE', 30, 'Designed template and wrote first 3 editions');
-- Insert 30 Lectures assigned to various interns
INSERT INTO lectures (name, progress, intern_id) VALUES
                                                     ('Java Basics', 'DONE', 36),
                                                     ('Spring Boot Intro', 'IN_PROGRESS', 36),
                                                     ('HTML & CSS', 'DONE', 37),
                                                     ('JavaScript Essentials', 'NOT_YET_STARTED', 37),
                                                     ('Git & GitHub', 'IN_PROGRESS', 38),
                                                     ('Python Fundamentals', 'DONE', 39),
                                                     ('Django Framework', 'IN_PROGRESS', 39),
                                                     ('React Basics', 'NOT_YET_STARTED', 40),
                                                     ('Node.js Introduction', 'DONE', 41),
                                                     ('SQL Databases', 'IN_PROGRESS', 41),
                                                     ('NoSQL Databases', 'NOT_YET_STARTED', 42),
                                                     ('REST API Design', 'DONE', 43),
                                                     ('GraphQL Basics', 'IN_PROGRESS', 43),
                                                     ('Docker Containers', 'NOT_YET_STARTED', 44),
                                                     ('Kubernetes', 'DONE', 45),
                                                     ('AWS Cloud', 'IN_PROGRESS', 45),
                                                     ('Azure Fundamentals', 'NOT_YET_STARTED', 36),
                                                     ('Google Cloud', 'DONE', 37),
                                                     ('DevOps Practices', 'IN_PROGRESS', 38),
                                                     ('CI/CD Pipelines', 'NOT_YET_STARTED', 39),
                                                     ('Testing Strategies', 'DONE', 40),
                                                     ('Agile Methodologies', 'IN_PROGRESS', 41),
                                                     ('Scrum Framework', 'NOT_YET_STARTED', 42),
                                                     ('Project Management', 'DONE', 43),
                                                     ('UI Design Principles', 'IN_PROGRESS', 44),
                                                     ('UX Research', 'NOT_YET_STARTED', 45),
                                                     ('Data Structures', 'DONE', 36),
                                                     ('Algorithms', 'IN_PROGRESS', 37),
                                                     ('System Design', 'NOT_YET_STARTED', 38),
                                                     ('Security Best Practices', 'DONE', 39);

-- Insert 100 Bookings spanning various rooms and users
INSERT INTO bookings (date, start_time, end_time, room_id, freelancer_id, intern_id, amount_paid, paid) VALUES
-- May 2025 bookings (10)
('2025-05-01', '09:00:00', '11:00:00', 1, 16, NULL, 15.00, TRUE),
('2025-05-01', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-05-02', '10:00:00', '12:00:00', 3, 17, NULL, 18.00, TRUE), -- Corrected price for room 3 (4*9=18 for 2 hours)
('2025-05-02', '14:00:00', '15:30:00', 4, NULL, 37, 7.50, TRUE), -- Corrected price for room 4 (10*5.00 * 1.5hr = 7.5)
('2025-05-03', '11:00:00', '13:00:00', 5, 18, NULL, 12.00, FALSE), -- Corrected price for room 5 (12*6.00 * 2hr = 12)
('2025-05-03', '09:30:00', '11:00:00', 6, 19, NULL, 6.75, TRUE), -- Corrected price for room 6 (8*4.50 * 1.5hr = 6.75)
('2025-05-04', '12:00:00', '14:00:00', 7, NULL, 38, 30.00, FALSE), -- Corrected price for room 7 (5*15.00 * 2hr = 30)
('2025-05-04', '10:00:00', '12:00:00', 1, 20, NULL, 15.00, TRUE),
('2025-05-05', '08:00:00', '10:00:00', 2, NULL, 39, 17.00, TRUE),
('2025-05-05', '15:00:00', '16:30:00', 8, NULL, 40, 27.00, FALSE), -- Corrected price for room 8 (8*18.00 * 1.5hr = 27)

-- June 2025 bookings (10)
('2025-06-01', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-06-01', '13:00:00', '15:00:00', 2, NULL, 41, 17.00, TRUE),
('2025-06-02', '10:00:00', '12:00:00', 3, 22, NULL, 18.00, TRUE),
('2025-06-02', '14:00:00', '15:30:00', 4, NULL, 42, 7.50, TRUE),
('2025-06-03', '11:00:00', '13:00:00', 5, 23, NULL, 12.00, FALSE),
('2025-06-03', '09:30:00', '11:00:00', 6, 24, NULL, 6.75, TRUE),
('2025-06-04', '12:00:00', '14:00:00', 7, NULL, 43, 30.00, FALSE),
('2025-06-04', '10:00:00', '12:00:00', 1, 25, NULL, 15.00, TRUE),
('2025-06-05', '08:00:00', '10:00:00', 2, NULL, 44, 17.00, TRUE),
('2025-06-05', '15:00:00', '16:30:00', 8, NULL, 45, 27.00, FALSE),

-- July 2025 bookings (10)
('2025-07-01', '09:00:00', '11:00:00', 1, 26, NULL, 15.00, TRUE),
('2025-07-01', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-07-02', '10:00:00', '12:00:00', 3, 27, NULL, 18.00, TRUE),
('2025-07-02', '14:00:00', '15:30:00', 4, NULL, 37, 7.50, TRUE),
('2025-07-03', '11:00:00', '13:00:00', 5, 28, NULL, 12.00, FALSE),
('2025-07-03', '09:30:00', '11:00:00', 6, 29, NULL, 6.75, TRUE),
('2025-07-04', '12:00:00', '14:00:00', 7, NULL, 38, 30.00, FALSE),
('2025-07-04', '10:00:00', '12:00:00', 1, 30, NULL, 15.00, TRUE),
('2025-07-05', '08:00:00', '10:00:00', 2, NULL, 39, 17.00, TRUE),
('2025-07-05', '15:00:00', '16:30:00', 8, NULL, 40, 27.00, FALSE),

-- August 2025 bookings (10)
('2025-08-01', '09:00:00', '11:00:00', 1, 16, NULL, 15.00, TRUE),
('2025-08-01', '13:00:00', '15:00:00', 2, NULL, 41, 17.00, TRUE),
('2025-08-02', '10:00:00', '12:00:00', 3, 17, NULL, 18.00, TRUE),
('2025-08-02', '14:00:00', '15:30:00', 4, NULL, 42, 7.50, TRUE),
('2025-08-03', '11:00:00', '13:00:00', 5, 18, NULL, 12.00, FALSE),
('2025-08-03', '09:30:00', '11:00:00', 6, 19, NULL, 6.75, TRUE),
('2025-08-04', '12:00:00', '14:00:00', 7, NULL, 43, 30.00, FALSE),
('2025-08-04', '10:00:00', '12:00:00', 1, 20, NULL, 15.00, TRUE),
('2025-08-05', '08:00:00', '10:00:00', 2, NULL, 44, 17.00, TRUE),
('2025-08-05', '15:00:00', '16:30:00', 8, NULL, 45, 27.00, FALSE),

-- More bookings with different patterns (10)
('2025-05-10', '08:00:00', '10:00:00', 9, NULL, 36, 40.00, TRUE), -- Room 9 (10*20 * 2hr = 40)
('2025-05-10', '10:30:00', '12:00:00', 10, 16, NULL, 37.50, TRUE), -- Room 10 (3*25 * 1.5hr = 37.5)
('2025-05-11', '13:00:00', '15:00:00', 11, NULL, 37, 60.00, FALSE), -- Room 11 (2*30 * 2hr = 60)
('2025-05-12', '14:00:00', '16:00:00', 12, 17, NULL, 44.00, TRUE), -- Room 12 (4*22 * 2hr = 44)
('2025-05-13', '09:00:00', '11:00:00', 13, NULL, 38, 20.00, TRUE), -- Room 13 (12*10 * 2hr = 20)
('2025-05-14', '11:30:00', '13:00:00', 14, 18, NULL, 18.00, FALSE), -- Room 14 (15*12 * 1.5hr = 18)
('2025-05-15', '15:00:00', '17:00:00', 15, NULL, 39, 16.00, TRUE), -- Room 15 (8*8 * 2hr = 16)
('2025-05-16', '10:00:00', '12:00:00', 16, 19, NULL, 60.00, TRUE), -- Room 16 (20*30 * 2hr = 60)
('2025-05-17', '08:30:00', '10:00:00', 17, NULL, 40, 52.50, FALSE), -- Room 17 (25*35 * 1.5hr = 52.5)
('2025-05-18', '12:00:00', '14:00:00', 18, 20, NULL, 50.00, TRUE), -- Room 18 (15*25 * 2hr = 50)

-- Recurring bookings for some users (6 + 5 = 11)
('2025-05-20', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-05-21', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-05-22', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-05-23', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-05-24', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),
('2025-05-25', '09:00:00', '11:00:00', 1, 21, NULL, 15.00, TRUE),

('2025-06-10', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-06-11', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-06-12', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-06-13', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),
('2025-06-14', '13:00:00', '15:00:00', 2, NULL, 36, 17.00, TRUE),

-- Group bookings (assuming NULL for freelancer/intern means admin or external group) (4)
-- Prices adjusted based on room 7 (MEETING_ROOM, 5, 15.00)
('2025-07-15', '09:00:00', '12:00:00', 7, NULL, NULL, 45.00, TRUE), -- 15 * 3 hours
('2025-07-15', '13:00:00', '16:00:00', 7, NULL, NULL, 45.00, TRUE), -- 15 * 3 hours, different time
('2025-07-22', '13:00:00', '16:00:00', 7, NULL, NULL, 45.00, TRUE), -- 15 * 3 hours
('2025-07-29', '10:00:00', '13:00:00', 7, NULL, NULL, 45.00, TRUE), -- 15 * 3 hours

-- Long duration bookings (3)
-- Prices adjusted based on room 5 (OPEN_ROOM, 12, 6.00)
('2025-08-10', '08:00:00', '18:00:00', 5, 22, NULL, 60.00, FALSE), -- 6 * 10 hours
('2025-08-11', '08:00:00', '18:00:00', 5, 22, NULL, 60.00, FALSE), -- 6 * 10 hours
('2025-08-12', '08:00:00', '18:00:00', 5, 22, NULL, 60.00, FALSE), -- 6 * 10 hours

-- Early morning and late evening bookings (completed and new ones) (5 + 17 = 22 to reach 100)
('2025-05-30', '07:00:00', '08:30:00', 3, 23, NULL, 13.50, TRUE), -- Room 3 (4*9.00 * 1.5hr = 13.5)
('2025-05-30', '18:00:00', '20:00:00', 4, NULL, 37, 10.00, FALSE), -- Room 4 (10*5.00 * 2hr = 10)
('2025-06-30', '06:30:00', '08:00:00', 19, 24, NULL, 18.00, TRUE), -- Room 19 (BRAINSTORMING_ROOM, 6, 12.00) * 1.5hr
('2025-06-30', '19:00:00', '20:30:00', 20, NULL, 38, 12.00, FALSE), -- Room 20 (QUIET_ZONE, 4, 8.00) * 1.5hr
('2025-07-30', '07:00:00', '09:00:00', 9, 25, NULL, 40.00, TRUE), -- Room 9 (10*20.00 * 2hr = 40)

-- Additional bookings to reach 100 (Total so far: 10+10+10+10+10+11+4+3+5 = 73. Need 27 more)
('2025-08-15', '10:00:00', '11:00:00', 10, 26, NULL, 25.00, TRUE), -- Room 10 (3*25.00 * 1hr = 25)
('2025-08-15', '14:00:00', '16:00:00', 11, NULL, 42, 60.00, FALSE), -- Room 11 (2*30.00 * 2hr = 60)
('2025-08-16', '09:00:00', '10:30:00', 12, 27, NULL, 33.00, TRUE), -- Room 12 (4*22.00 * 1.5hr = 33)
('2025-08-16', '11:00:00', '12:00:00', 13, NULL, 43, 10.00, TRUE), -- Room 13 (12*10.00 * 1hr = 10)
('2025-08-17', '13:00:00', '15:30:00', 14, 28, NULL, 30.00, FALSE), -- Room 14 (15*12.00 * 2.5hr = 30)
('2025-08-17', '16:00:00', '17:00:00', 15, NULL, 44, 8.00, TRUE), -- Room 15 (8*8.00 * 1hr = 8)
('2025-08-18', '09:30:00', '11:30:00', 16, 29, NULL, 60.00, TRUE), -- Room 16 (20*30.00 * 2hr = 60)
('2025-08-18', '14:00:00', '15:00:00', 17, NULL, 45, 35.00, FALSE), -- Room 17 (25*35.00 * 1hr = 35)
('2025-08-19', '10:00:00', '12:30:00', 18, 30, NULL, 62.50, TRUE), -- Room 18 (15*25.00 * 2.5hr = 62.5)
('2025-08-19', '13:00:00', '14:30:00', 19, NULL, 36, 18.00, TRUE), -- Room 19 (6*12.00 * 1.5hr = 18)
('2025-08-20', '15:00:00', '17:00:00', 20, 16, NULL, 16.00, FALSE), -- Room 20 (4*8.00 * 2hr = 16)
('2025-08-20', '09:00:00', '10:00:00', 1, NULL, 37, 7.50, TRUE), -- Room 1 (8*7.50 * 1hr = 7.5)
('2025-08-21', '11:00:00', '13:00:00', 2, 17, NULL, 17.00, TRUE), -- Room 2 (6*8.50 * 2hr = 17)
('2025-08-21', '14:30:00', '16:00:00', 3, NULL, 38, 13.50, FALSE), -- Room 3 (4*9.00 * 1.5hr = 13.5)
('2025-08-22', '10:00:00', '11:30:00', 4, 18, NULL, 7.50, TRUE), -- Room 4 (10*5.00 * 1.5hr = 7.5)
('2025-08-22', '13:00:00', '14:00:00', 6, NULL, 39, 4.50, TRUE), -- Room 6 (8*4.50 * 1hr = 4.5)
('2025-08-23', '09:00:00', '12:00:00', 8, 19, NULL, 54.00, FALSE), -- Room 8 (8*18.00 * 3hr = 54)
('2025-08-23', '14:00:00', '15:30:00', 9, NULL, 40, 30.00, TRUE), -- Room 9 (10*20.00 * 1.5hr = 30)
('2025-08-24', '10:30:00', '12:00:00', 10, 20, NULL, 37.50, TRUE), -- Room 10 (3*25.00 * 1.5hr = 37.5)
('2025-08-24', '13:00:00', '16:00:00', 11, NULL, 41, 90.00, FALSE), -- Room 11 (2*30.00 * 3hr = 90)
('2025-08-25', '09:00:00', '11:00:00', 13, 21, NULL, 20.00, TRUE), -- Room 13 (12*10.00 * 2hr = 20)
('2025-08-25', '14:00:00', '15:00:00', 15, NULL, 42, 8.00, TRUE), -- Room 15 (8*8.00 * 1hr = 8)
('2025-08-26', '10:00:00', '13:00:00', 16, 22, NULL, 90.00, FALSE), -- Room 16 (20*30.00 * 3hr = 90)
('2025-08-26', '15:00:00', '16:30:00', 17, NULL, 43, 52.50, TRUE), -- Room 17 (25*35.00 * 1.5hr = 52.5)
('2025-08-27', '09:30:00', '11:00:00', 18, 23, NULL, 37.50, TRUE), -- Room 18 (15*25.00 * 1.5hr = 37.5)
('2025-08-27', '13:00:00', '14:00:00', 19, NULL, 44, 12.00, FALSE), -- Room 19 (6*12.00 * 1hr = 12)
('2025-08-28', '16:00:00', '18:00:00', 20, 24, NULL, 16.00, TRUE); -- Room 20 (4*8.00 * 2hr = 16)
-- Total Bookings = 73 + 27 = 100

-- Insert Lecture Files (assuming lectures have IDs 1-30 from previous inserts)
-- Let's assume lecture_files table has (id AUTO_INCREMENT, file_name, file_type, lecture_id FOREIGN KEY)
/*INSERT INTO lecture_files (file_name, file_type, lecture_id) VALUES
                                                                 ('Java_Basics_Slides.pdf', 'PDF', 1),
                                                                 ('Java_Basics_Exercises.docx', 'DOCX', 1),
                                                                 ('SpringBoot_Intro_Video.mp4', 'MP4', 2),
                                                                 ('SpringBoot_Project_Setup.zip', 'ZIP', 2),
                                                                 ('HTML_CSS_Cheatsheet.pdf', 'PDF', 3),
                                                                 ('JavaScript_DOM_Manipulation.js', 'JS', 4),
                                                                 ('Git_Commands.txt', 'TXT', 5),
                                                                 ('Python_Syntax_Guide.pdf', 'PDF', 6),
                                                                 ('Django_Models_Tutorial.html', 'HTML', 7),
                                                                 ('React_Components_Examples.jsx', 'JSX', 8),
                                                                 ('NodeJS_Async_Patterns.pdf', 'PDF', 9),
                                                                 ('SQL_Joins_Explained.png', 'PNG', 10),
                                                                 ('NoSQL_CAP_Theorem.pdf', 'PDF', 11),
                                                                 ('REST_API_Best_Practices.md', 'MD', 12),
                                                                 ('GraphQL_Schema_Example.gql', 'GQL', 13),
                                                                 ('Docker_Dockerfile_Example.txt', 'TXT', 14),
                                                                 ('Kubernetes_Pods_Services.pdf', 'PDF', 15),
                                                                 ('AWS_EC2_Setup.docx', 'DOCX', 16),
                                                                 ('Azure_VM_Creation.mp4', 'MP4', 17),
                                                                 ('GCP_AppEngine_Intro.pdf', 'PDF', 18),
                                                                 ('DevOps_Culture.pdf', 'PDF', 19),
                                                                 ('Jenkins_Pipeline_Script.groovy', 'GROOVY', 20),
                                                                 ('JUnit_Testing_Guide.pdf', 'PDF', 21),
                                                                 ('Agile_Manifesto.txt', 'TXT', 22),
                                                                 ('Scrum_Roles_Responsibilities.png', 'PNG', 23),
                                                                 ('Gantt_Chart_Template.xlsx', 'XLSX', 24),
                                                                 ('UI_Color_Theory.pdf', 'PDF', 25),
                                                                 ('User_Persona_Template.docx', 'DOCX', 26),
                                                                 ('LinkedList_Implementation.java', 'JAVA', 27),
                                                                 ('Sorting_Algorithms_Comparison.pdf', 'PDF', 28),
                                                                 ('System_Design_Patterns.md', 'MD', 29),
                                                                 ('OWASP_Top_10.pdf', 'PDF', 30);*/