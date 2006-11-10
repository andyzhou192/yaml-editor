package com.piece_framework.piece_ide.yamleditor.editors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kwalify.SyntaxException;
import kwalify.Util;
import kwalify.ValidationException;
import kwalify.Validator;
import kwalify.YamlParser;

import org.eclipse.core.resources.IMarker;



/**
 * YAML �o���f�[�^�[.
 * YAML�t�@�C���̃o���f�[�V���������s����B
 * 
 * @author Seiichi Sugimoto
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class YAMLValidater {

    /**
     * Kwalify���C�u�������g�p���A�o���f�[�V���������s����.
     * 
     * @param schemaStream YAML�X�L�[�}��`�t�@�C���i�X�g���[���j
     * @param docStream YAML�t�@�C���i�X�g���[���j
     * @return �G���[���X�g
     * @throws IOException ���o�͗�O�G���[
     * @see org.eclipse.ui.editors.text.StorageDocumentProvider
     *          #createDocument(java.lang.Object)
     */
    protected static List<Map> validation(InputStream schemaStream,
                                            InputStream docStream)
                                            throws IOException {

        List<Map> errorList = new ArrayList();
     
        try {
            //YAML�X�L�[�}�ݒ�
            Object schema = new YamlParser(Util.untabify(
                         Util.readInputStream(schemaStream))).parse();
            
            //YAML�t�@�C���ݒ�
            YamlParser parser = new YamlParser(Util.untabify(Util
                               .readInputStream(docStream)));
            Object document = parser.parse();
        
            //�o���f�[�V�����̎��s
            Validator validator = new Validator(schema);
            List errors = validator.validate(document);

            //�G���[���e���G���[�o�͗p���X�g�ɃZ�b�g
            if (errors != null && errors.size() > 0) {
                parser.setErrorsLineNumber(errors);
                Collections.sort(errors);
                for (Iterator it = errors.iterator(); it.hasNext();) {
                    ValidationException error = (ValidationException) it.next();

                    Map<String, Comparable> attributeMap = new HashMap();
                    attributeMap.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
                    attributeMap.put(IMarker.MESSAGE, error.getMessage());
                    attributeMap.put(IMarker.LINE_NUMBER,
                                                      error.getLineNumber());
                    errorList.add(attributeMap);
                }
            }
        } catch (SyntaxException e) {
            Map<String, Comparable> attributeMap = new HashMap();
            attributeMap.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
            attributeMap.put(IMarker.MESSAGE, e.getMessage());
            attributeMap.put(IMarker.LINE_NUMBER, e.getLineNumer());
            errorList.add(attributeMap);
        }
        return errorList;
    }
}