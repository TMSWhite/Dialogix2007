--
-- Query to get denormalized result set for a given session in order to create HL7 2.x messages
--
SELECT
	iss.instrument_version_id,
	i.name as instrument_name,
	iv.name as instrument_version,
	
	iss.dialogix_user_id,
	
	iss.start_time,
	iss.last_access_time,
	
	ic.item_id,
	vn.name as var_name,
	
	de.data_element_sequence,
	de.question_as_asked,
	
	de.answer_string,
	de.answer_id,
	de.answer_code,
	
	CONCAT(iss.instrument_version_id, '^', i.name, ' (', iv.name, ')^L') as obr4,
	CONCAT(ic.item_id, '^^L^', vn.name, '^', de.question_as_asked, '^L') as obx3,
	CONCAT(de.answer_id, '^', de.answer_string, '^L^', de.answer_code, '^^L') as obx5
	
FROM 
	data_elements as de,
	instruments as i,
	instrument_contents as ic,
	instrument_sessions as iss,
	instrument_versions as iv,
	var_names as vn
WHERE
	iss.id = ?
	AND de.instrument_session_id = iss.id
	AND de.instrument_content_id = ic.id
	AND de.var_name_id = vn.id
	AND iss.instrument_version_id = iv.id
	AND iv.instrument_id = i.id
ORDER BY 
	de.data_element_sequence