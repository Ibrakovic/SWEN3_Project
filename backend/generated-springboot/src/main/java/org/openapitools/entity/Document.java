package org.openapitools.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_size")
	private Long fileSize;

	@Column(name = "content")
	private String content;

	@Column(name = "upload_date")
	private LocalDateTime uploadDate;

}
