package com.piece_framework.piece_ide.yamleditor.editors;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import kwalify.Util;
import kwalify.ValidationException;
import kwalify.Validator;
import kwalify.YamlParser;


/**
 * YAML �o���f�[�^�[.
 * YAML�t�@�C���̃o���f�[�V���������s����B
 * 
 * @author Seiichi Sugimoto
 * @version 0.2.0
 * @since 0.2.0
 * 
 */
public final class YAMLValidater {

    /**
     * 
     */
    private YAMLValidater() {
    }
    
    /**
     * �o���f�[�V���������s����.
     * 
     * @param yamlSchemaStr �X�L�[�}
     * @param yamlStr YAML�e�L�X�g
     * @throws Exception ���ʗ�O
     * @throws IOException 
     * @throws SyntaxException 
     * @see org.eclipse.ui.editors.text.StorageDocumentProvider
     *          #createDocument(java.lang.Object)
     */
      @SuppressWarnings("unchecked")
    public static void validation(String yamlSchemaStr, 
                                      String yamlStr) throws Exception {

        //YAML�X�L�[�}�ݒ�
        Object schema = new YamlParser(Util.untabify(yamlSchemaStr)).parse();

        //YAML�t�@�C���̓ǂݍ���
        YamlParser parser = new YamlParser(Util.untabify(yamlStr));
        Object document = parser.parse();

        //�o���f�[�V�����̎��s
        Validator validator = new Validator(schema);
        List errors = validator.validate(document);

        //�G���[�o��
        if (errors != null && errors.size() > 0) {
            parser.setErrorsLineNumber(errors);
            Collections.sort(errors);
            for (Iterator it = errors.iterator(); it.hasNext();) {
                ValidationException error = (ValidationException) it.next();
                int linenum = error.getLineNumber();
                String path = error.getPath();
                String mesg = error.getMessage();
                System.out.println("- " + linenum + ": [" + path + "] " + mesg);
            }
        }
    }
}