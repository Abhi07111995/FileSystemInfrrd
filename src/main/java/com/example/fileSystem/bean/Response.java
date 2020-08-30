package com.example.fileSystem.bean;


import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {

	private boolean success;
	Object result;

}

