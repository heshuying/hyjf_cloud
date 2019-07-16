package com.hyjf.admin.service;

import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;

public interface TemplateDisposeService {

	TemplateDisposeResponse templateDisposeList(TemplateDisposeRequest templateDisposeRequest);

	TemplateDisposeResponse updateTemplateDispose(TemplateDisposeRequest templateDisposeRequest);

	TemplateDisposeResponse insertTemplateDispose(TemplateDisposeRequest templateDisposeRequest);

	TemplateDisposeResponse deleteTemplateDispose(TemplateDisposeRequest templateDisposeRequest);

}
