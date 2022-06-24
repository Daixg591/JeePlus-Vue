package com.jeeplus.flowable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.flowable.service.FlowableModelService;
import com.jeeplus.flowable.model.FlowModel;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.exception.ConflictingRequestException;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelKeyRepresentation;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * 流程模型相关Controller
 * @author liugaofeng
 * @version 2021-07-29
 */
@RestController
@RequestMapping("/flowable/model")
public class FlowableModelController {

	@Autowired
	private FlowableModelService flowableModelService;
	@Autowired
	protected ModelService modelService;
	@Autowired
	protected ModelRepository modelRepository;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	private RepositoryService repositoryService;


	@GetMapping(value="account", produces = "application/json")
	public UserRepresentation account() {
		UserDTO user = UserUtils.getCurrentUserDTO ( );
		UserEntityImpl fUser = new UserEntityImpl();

		fUser.setId(user.getId());
		fUser.setFirstName(user.getName());
		fUser.setLastName("");
		fUser.setEmail(user.getEmail());

		UserRepresentation userRepresentation = new UserRepresentation(fUser);

		return userRepresentation;
	}

	/**
	 * 流程模型列表
	 */
	@GetMapping("list")
	public ResponseEntity data(Page<FlowModel> page, String filter, HttpServletRequest request, HttpServletResponse response) {
		page = flowableModelService.getModels(page,filter, "modifiedDesc", 0, request);
		return ResponseEntity.ok ( page );
	}


	/**
	 * 导出model的xml文件
	 */
	@GetMapping("getBpmnXml")
	public String export(String id, HttpServletResponse response) {
		return flowableModelService.export(id, response);
	}

	/**
	 * 更新Model分类
	 */
	@PutMapping( value = "updateCategory")
	public ResponseEntity updateCategory(String id, String category) {
		repositoryService.setProcessDefinitionCategory(id, category);
		return ResponseEntity.ok ("设置成功，模块ID=" + id);
	}

	/**
	 * 删除Model
	 *
	 * @param ids
	 * @return
	 */
	@DemoMode
	@DeleteMapping("delete")
	public ResponseEntity deleteAll(String ids) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			flowableModelService.delete(id);
		}
		return ResponseEntity.ok ("删除成功!");
	}
	/**
	 * 根据Model复制流程
	 */
	@GetMapping("copy")
	public ResponseEntity copy(String id) throws Exception{

		org.flowable.ui.modeler.domain.Model sourceModel = modelService.getModel(id);
		ModelRepresentation modelRepresentation = new ModelRepresentation ();
		modelRepresentation.setKey ("Process_"+ UUID.randomUUID ());
		modelRepresentation.setName (sourceModel.getName ()+"_copy");
		modelRepresentation.setModelType (0);
		modelRepresentation.setDescription ("");
		modelRepresentation.setKey(modelRepresentation.getKey().replaceAll(" ", ""));
		this.checkForDuplicateKey(modelRepresentation);
		String json = modelService.createModelJson(modelRepresentation);
		UserDTO user = UserUtils.getCurrentUserDTO ();
		UserEntityImpl fUser = new UserEntityImpl();

		fUser.setId(user.getId());
		fUser.setFirstName(user.getName());
		fUser.setLastName("");
		fUser.setEmail(user.getEmail());

		org.flowable.ui.modeler.domain.Model newModel = modelService.createModel(modelRepresentation, json, fUser);
		String modelId = newModel.getId ();


		ObjectNode sourceObjectNode = (ObjectNode) objectMapper.readTree(sourceModel.getModelEditorJson());
		ObjectNode editorNode = sourceObjectNode.deepCopy();
		ObjectNode properties = objectMapper.createObjectNode();
		properties.put("process_id", newModel.getKey());
		properties.put("name", newModel.getName());
		editorNode.set("properties", properties);

		newModel.setModelEditorJson (editorNode.toString());


		modelService.saveModel(modelId, newModel.getName (), newModel.getKey (), newModel.getDescription (), newModel.getModelEditorJson (), true, "", fUser);

		return  ResponseEntity.ok ("拷贝成功!");
	}

	/**
	 * 根据Model部署流程
	 */
	@PutMapping("deploy")
	public ResponseEntity deploy(String id, String category) {
		String result = flowableModelService.deploy(id, category);
		return  ResponseEntity.ok (result);
	}



	@PostMapping(
			value = {"/rest/models"},
			produces = {"application/json"}
	)
	public ModelRepresentation createModel(@RequestBody ModelRepresentation modelRepresentation) {
		modelRepresentation.setKey(modelRepresentation.getKey().replaceAll(" ", ""));
		this.checkForDuplicateKey(modelRepresentation);
		String json = this.modelService.createModelJson(modelRepresentation);
		Model newModel = this.modelService.createModel(modelRepresentation, json, SecurityUtils.getCurrentUserObject());
		return new ModelRepresentation(newModel);
	}
	@PostMapping("saveModel/{modelId}")
	public ModelRepresentation saveModel(@PathVariable String modelId,  @RequestBody MultiValueMap<String, String> values) {
		long lastUpdated = -1L;
		String lastUpdatedString = (String) values.getFirst("lastUpdated");
		if (lastUpdatedString == null) {
			throw new BadRequestException("Missing lastUpdated date");
		} else {
			try {
				Date readValue = this.objectMapper.getDeserializationConfig().getDateFormat().parse(lastUpdatedString);
				lastUpdated = readValue.getTime();
			} catch (ParseException var12) {
				throw new BadRequestException("Invalid lastUpdated date: '" + lastUpdatedString + "'");
			}

			Model model = this.modelService.getModel(modelId);
			boolean currentUserIsOwner = model.getLastUpdatedBy().equals(UserUtils.getCurrentUserDTO ().getId());
			String resolveAction =  values.getFirst("conflictResolveAction");
			if (model.getLastUpdated().getTime() != lastUpdated) {
				String isNewVersionString;
				if ("saveAs".equals(resolveAction)) {
					isNewVersionString =  values.getFirst("saveAs");
					String json =  values.getFirst("json_xml");
					json = this.flowableModelService.changeXmlToJson(json);
					return this.createNewModel(isNewVersionString, model.getDescription(), model.getModelType(), json);
				} else if ("overwrite".equals(resolveAction)) {
					return this.updateModel(model, values, false);
				} else if ("newVersion".equals(resolveAction)) {
					return this.updateModel(model, values, true);
				} else {
					isNewVersionString =  values.getFirst("newversion");
					if (currentUserIsOwner && "true".equals(isNewVersionString)) {
						return this.updateModel(model, values, true);
					} else {
						ConflictingRequestException exception = new ConflictingRequestException("Process model was updated in the meantime");
						exception.addCustomData("userFullName", model.getLastUpdatedBy());
						exception.addCustomData("newVersionAllowed", currentUserIsOwner);
						throw exception;
					}
				}
			} else {
				return this.updateModel(model, values, false);
			}
		}
	   }

		protected ModelRepresentation updateModel(Model model, MultiValueMap<String, String> values, boolean forceNewVersion) {
			String name = values.getFirst("name");
			String key = (values.getFirst("key")).replaceAll(" ", "");
			String description = values.getFirst("description");
			String isNewVersionString = values.getFirst("newversion");
			String newVersionComment = null;
			ModelKeyRepresentation modelKeyInfo = this.modelService.validateModelKey(model, model.getModelType(), key);
			if (modelKeyInfo.isKeyAlreadyExists()) {
				throw new BadRequestException("Model with provided key already exists " + key);
			} else {
				boolean newVersion = false;
				if (forceNewVersion) {
					newVersion = true;
					newVersionComment = values.getFirst("comment");
				} else if (isNewVersionString != null) {
					newVersion = "true".equals(isNewVersionString);
					newVersionComment = values.getFirst("comment");
				}

				String json = values.getFirst("json_xml");
				json = this.flowableModelService.changeXmlToJson(json);

				try {
					ObjectNode editorJsonNode = (ObjectNode)this.objectMapper.readTree(json);
					ObjectNode propertiesNode = (ObjectNode)editorJsonNode.get("properties");
					propertiesNode.put("process_id", key);
					propertiesNode.put("name", name);
					if (StringUtils.isNotEmpty(description)) {
						propertiesNode.put("documentation", description);
					}

					editorJsonNode.set("properties", propertiesNode);
					model = this.modelService.saveModel(model.getId(), name, key, description, editorJsonNode.toString(), newVersion, newVersionComment, SecurityUtils.getCurrentUserObject());
					return new ModelRepresentation(model);
				} catch (Exception var15) {
					throw new BadRequestException("Process model could not be saved " + model.getId());
				}
			}
		}


	protected void checkForDuplicateKey(ModelRepresentation modelRepresentation) {
		ModelKeyRepresentation modelKeyInfo = this.modelService.validateModelKey((Model)null, modelRepresentation.getModelType(), modelRepresentation.getKey());
		if (modelKeyInfo.isKeyAlreadyExists()) {
			throw new ConflictingRequestException("Provided model key already exists: " + modelRepresentation.getKey());
		}
	}


	protected ModelRepresentation createNewModel(String name, String description, Integer modelType, String editorJson) {
			ModelRepresentation model = new ModelRepresentation();
			model.setName(name);
			model.setDescription(description);
			model.setModelType(modelType);
			Model newModel = this.modelService.createModel(model, editorJson, SecurityUtils.getCurrentUserObject());
			return new ModelRepresentation(newModel);
		}
	}

