-- Revision History:
--         DATE            NAME                 COMMENTS
--     2025-05-31     DERRICK MANGARI     CREATED FILE FOR STORED PROCEDURE 
--     2025-06-01     DERRICK MANGARI     CREATED CRUD OPERATIONS STORED PROCEDURES
--     2025-06-01     DERRICK MANGARI     MODIFIED LOGIN STORED PROCEDURE


--USER STORED PROCEDURES

-- Insert a user
CREATE PROCEDURE insert_user(p_user_id UUID, p_username VARCHAR, p_email VARCHAR, p_password VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO users(user_id, username, email, password)
    VALUES (p_user_id, p_username, p_email, p_password);
END;
$$;

--Update a user
CREATE PROCEDURE update_user(p_user_id UUID, p_username VARCHAR, p_email VARCHAR, p_password VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE users
	SET username = p_username,
		email = p_email,
		password = p_password,
		updated_at = CURRENT_TIMESTAMP
	WHERE user_id = p_user_id;
END;
$$;

--Select all users
CREATE PROCEDURE select_all_users()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM users
	ORDER BY email;
END;
$$;

--select one row users
CREATE PROCEDURE select_one_user(p_user_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM users
	WHERE user_id = p_user_id;
END;
$$;

--delete a user
CREATE PROCEDURE delete_user(p_user_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM users
	WHERE user_id = p_user_id;
END;
$$;

--login user with email
CREATE PROCEDURE login_user(p_email VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
	SELECT user_id, email, username, password
	FROM users
	WHERE email = p_email;
END
$$;

--TAGS STORED PROCEDURES

-- Insert a tag
CREATE PROCEDURE insert_tag(p_tag_id UUID, p_name VARCHAR, p_user_id UUID, p_color VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO tags(tag_id, name, user_id, color)
    VALUES (p_tag_id, p_name, p_user_id, p_color);
END;
$$;


--Update a tag
CREATE PROCEDURE update_tag(p_tag_id UUID, p_name VARCHAR, p_user_id UUID, p_color VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE tags
	SET name = p_name,
		updated_at = CURRENT_TIMESTAMP,
		user_id = p_user_id,
		color = p_color
	WHERE tag_id = p_tag_id;
END;
$$;

--Select all tags
CREATE PROCEDURE select_all_tags()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM tags
	ORDER BY name;
END;
$$;

--select one row tags
CREATE PROCEDURE select_one_tag(p_tag_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM tags
	WHERE tag_id = p_tag_id;
END;
$$;

--delete a tag
CREATE PROCEDURE delete_tag(p_tag_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM tags
	WHERE tag_id = p_tag_id;
END;
$$;


--TASKS STORED PROCEDURE

-- Insert a task
CREATE PROCEDURE insert_task(
    p_task_id UUID,
    p_title VARCHAR,
    p_description TEXT,
    p_status task_status,
    p_due_date TIMESTAMP,
    p_priority task_priority,
    p_tag_id UUID,
    p_user_id UUID,
	p_recurring_task BOOLEAN,
	p_notification_reminder BOOLEAN,
	p_notification_date TIMESTAMP,
	p_reminder_time VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO tasks(task_id, title, description, status, due_date, priority, tag_id, user_id, recurring_task, notification_reminder, notification_date, reminder_time)
    VALUES (p_task_id, p_title, p_description, p_status, p_due_date, p_priority, p_tag_id, p_user_id, p_recurring_task, p_notification_reminder, p_notification_date, p_reminder_time);
END;
$$;


--Update a tasks
CREATE PROCEDURE update_task(
	p_task_id UUID,
    p_title VARCHAR,
    p_description TEXT,
    p_status task_status,
    p_due_date TIMESTAMP,
    p_priority task_priority,
    p_tag_id UUID,
    p_user_id UUID,
	p_recurring_task BOOLEAN,
	p_notification_reminder BOOLEAN,
	p_notification_date TIMESTAMP,
	p_reminder_time VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE tasks
	SET title = p_title,
		description = p_description,
		status = p_status,
		due_date = p_due_date,
		priority = p_priority,
		recurring_task = p_recurring_task,
		notification_reminder = p_notification_reminder,
		notification_date = p_notification_date,
		reminder_time = p_reminder_time,
		tag_id = p_tag_id,
		user_id = p_user_id,
		updated_at = CURRENT_TIMESTAMP
	WHERE task_id = p_task_id;
END;
$$;

--Select all tasks
CREATE PROCEDURE select_all_tasks()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM tasks
	ORDER BY created_at;
END;
$$;

--select one row tasks
CREATE PROCEDURE select_one_task(p_task_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM tasks
	WHERE task_id = p_task_id;
END;
$$;

--delete a task
CREATE PROCEDURE delete_task(p_task_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM tasks
	WHERE task_id = p_task_id;
END;
$$;


--SUBTASKS STORED PROCEDURE

-- Insert a subtask
CREATE PROCEDURE insert_subtask(
    p_subtask_id UUID,
    p_task_id UUID,
    p_title VARCHAR,
    p_description TEXT,
    p_is_done BOOLEAN,
	p_due_date TIMESTAMP,
	p_notification_reminder BOOLEAN,
	p_notification_date TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO subtasks(subtask_id, task_id, title, description, is_done, due_date, notification_reminder, notification_date)
    VALUES (p_subtask_id, p_task_id, p_title, p_description, p_is_done, p_due_date, p_notification_reminder, p_notification_date);
END;
$$;

--Update a subtask
CREATE PROCEDURE update_subtask(
	p_subtask_id UUID,
    p_task_id UUID,
    p_title VARCHAR,
    p_description TEXT,
    p_is_done BOOLEAN,
	p_due_date TIMESTAMP,
	p_notification_reminder BOOLEAN,
	p_notification_date TIMESTAMP)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE subtasks
	SET task_id = p_task_id,
		title = p_title,
		description = p_description,
		is_done = p_is_done,
		due_date = p_due_date,
		updated_at = CURRENT_TIMESTAMP,
		notification_reminder = p_notification_reminder,
		notification_date = p_notification_date
	WHERE subtask_id = p_subtask_id;
END;
$$;

--Select all subtasks
CREATE PROCEDURE select_all_subtasks()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM subtasks
	ORDER BY title;
END;
$$;

--select one row subtasks
CREATE PROCEDURE select_one_subtask(p_subtask_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT *
	FROM subtasks
	WHERE subtask_id = p_subtask_id ;
END;
$$;


--delete a subtask
CREATE PROCEDURE delete_subtask(p_subtask_id UUID)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM subtasks
	WHERE subtask_id = p_subtask_id;
END;
$$;