-------------------------------------------------------CREATING TABLES--------------------------------------------------
CREATE TABLE location
(
loc_id VARCHAR2(6) NOT NULL,
loc_city VARCHAR2(25) NOT NULL,
loc_state VARCHAR2(25) NOT NULL,
loc_country VARCHAR2(25),
loc_zip NUMBER(10),
loc_estab_year DATE
);

CREATE TABLE department
(
dept_id VARCHAR2(6) NOT NULL,
dept_name VARCHAR2(25) NOT NULL,
loc_id VARCHAR2(6) NOT NULL
);

CREATE TABLE employee_n
(
emp_id VARCHAR2(6) NOT NULL,
emp_f_name VARCHAR2(25) NOT NULL,
emp_l_name VARCHAR2(25) NOT NULL,
emp_house_no VARCHAR2(25) NOT NULL,
emp_str_no VARCHAR2(25),
emp_town VARCHAR2(25) NOT NULL,
emp_city VARCHAR2(25) NOT NULL,
emp_state VARCHAR2(25),
emp_zip NUMBER(10),
emp_dob DATE NOT NULL,
emp_cnic NUMBER(13) NOT NULL UNIQUE,
emp_mobile NUMBER(11) NOT NULL UNIQUE,
emp_email VARCHAR2(50) NOT NULL UNIQUE,
emp_rank VARCHAR2(25) NOT NULL,
emp_hire_date DATE NOT NULL,
dept_id VARCHAR2(6) NOT NULL,
password VARCHAR2(25) NOT NULL
);

CREATE TABLE applicant
(
app_id VARCHAR2(6) NOT NULL,
app_f_name VARCHAR2(25) NOT NULL,
app_l_name VARCHAR2(25) NOT NULL,
app_cnic NUMBER(13) NOT NULL UNIQUE,
app_mobile NUMBER(11) NOT NULL UNIQUE,
app_email VARCHAR2(50) NOT NULL UNIQUE,
password VARCHAR2(25) NOT NULL
);

CREATE TABLE document
(
doc_id VARCHAR2(6) NOT NULL,
doc_name VARCHAR2(50) NOT NULL,
doc_start_date DATE NOT NULL,
doc_due_date DATE,
doc_attachment NUMBER(13),
doc_status NUMBER(1),
emp_id VARCHAR2(6) NOT NULL,
app_id VARCHAR2(6) NOT NULL
);

CREATE TABLE document_status
(
docst_serial_no NUMBER(6) NOT NULL,
doc_id VARCHAR2(6) NOT NULL,
received_date DATE NOT NULL,
emp_id_sender VARCHAR2(6) NOT NULL,
emp_id_receiver VARCHAR2(6) NOT NULL,
comments VARCHAR2(50)
);

CREATE TABLE document_type
(
doc_name VARCHAR2(50) NOT NULL,
doc_pre_route NUMBER(1) NOT NULL,
req_attach VARCHAR2(50)
);

CREATE TABLE document_route
(
doc_step_no NUMBER(3) NOT NULL,
doc_name VARCHAR2(50) NOT NULL,
emp_id VARCHAR2(6) NOT NULL
);

---------------------------------------------PRIMARY KEYS----
ALTER TABLE location
ADD (CONSTRAINTS loc_id_pk
	PRIMARY KEY(loc_id)
	);

ALTER TABLE department
ADD (CONSTRAINTS dept_id_pk
	PRIMARY KEY(dept_id)
	);

ALTER TABLE employee_n
ADD (CONSTRAINTS emp_id_pk
	PRIMARY KEY(emp_id)
	);
	
ALTER TABLE applicant
ADD (CONSTRAINTS app_id_pk
	PRIMARY KEY(app_id)
	);

ALTER TABLE document
ADD (CONSTRAINTS doc_id_pk
	PRIMARY KEY(doc_id)
	);

ALTER TABLE document_status
ADD (CONSTRAINTS document_status_id_pk
	PRIMARY KEY(docst_serial_no,doc_id)
	);
	
ALTER TABLE document_type
ADD (CONSTRAINTS document_type_id_pk
	PRIMARY KEY(doc_name)
	);
	
ALTER TABLE document_route
ADD (CONSTRAINTS document_route_id_pk
	PRIMARY KEY(doc_step_no,doc_name,emp_id)
	)
ADD (CONSTRAINTS document_route_id_unique
	UNIQUE (doc_name,emp_id)
	);

---------------------------------------------FOREIGN KEYS----	

ALTER TABLE department
ADD (CONSTRAINTS dept_loc_id_fk
	FOREIGN KEY(loc_id)
	REFERENCES location (loc_id)
	);

ALTER TABLE employee_n
ADD (CONSTRAINTS emp_dept_id_fk
	FOREIGN KEY(dept_id)
	REFERENCES department (dept_id)
	);

ALTER TABLE document
ADD (CONSTRAINTS doc_emp_id_fk
	FOREIGN KEY(emp_id)
	REFERENCES employee_n (emp_id)
	)
ADD (CONSTRAINTS doc_app_id_fk
	FOREIGN KEY (app_id)
	REFERENCES applicant (app_id)
	)
ADD (CONSTRAINTS doc_name_fk
	FOREIGN KEY (doc_name)
	REFERENCES document_type (doc_name)
	);

ALTER TABLE document_status
ADD (CONSTRAINTS docst_doc_id_fk
	FOREIGN KEY(doc_id)
	REFERENCES document (doc_id)
	)
ADD (CONSTRAINTS docst_emp_id_rec_fk
	FOREIGN KEY(emp_id_receiver)
	REFERENCES employee_n (emp_id)
	)
ADD (CONSTRAINTS docst_emp_id_sen_fk
	FOREIGN KEY(emp_id_sender)
	REFERENCES employee_n (emp_id)
	);
	
ALTER TABLE document_route
ADD (CONSTRAINTS docrt_emp_id_fk
	FOREIGN KEY(emp_id)
	REFERENCES employee_n (emp_id)
	)
ADD (CONSTRAINTS docrt_name_fk
	FOREIGN KEY (doc_name)
	REFERENCES document_type (doc_name)
	);
	
COMMIT;