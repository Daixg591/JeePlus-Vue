package com.jeeplus.core.excel;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.jeeplus.sys.service.dto.DictValueDTO;
import com.jeeplus.sys.utils.DictUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟使用,生产请用真实字典
 *
 */
public class ExcelDiceAddressListHandlerImpl implements IExcelDictHandler {

    /**
     * 返回字典所有值
     * key: dictKey
     *
     * @param dict 字典Key
     * @return
     */
    @Override
    public List<Map> getList(String dict) {
        List<Map> list = new ArrayList<> ();
//        Map<String, String> dictMap = new HashMap<>();
//        dictMap.put("dictKey", "0");
//        dictMap.put("dictValue", "严重瞌睡");
//        list.add(dictMap);
//        dictMap = new HashMap<>();
//        dictMap.put("dictKey", "1");
//        dictMap.put("dictValue", "小B");
//        list.add(dictMap);
//        dictMap = new HashMap<>();
//        dictMap.put("dictKey", "1");
//        dictMap.put("dictValue", "深度富有");
//        list.add(dictMap);
        List<DictValueDTO> dictValueDTOs = DictUtils.getDictMap ().get (dict);
        dictValueDTOs.forEach (dictValueDTO -> {
            Map<String, String> dictMap = new HashMap<> ();
            dictMap.put ("dictKey", dictValueDTO.getValue ());
            dictMap.put ("dictValue", dictValueDTO.getLabel ());
            list.add (dictMap);
        });
        return list;
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        return DictUtils.getDictLabel (value.toString (), dict, null);
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        return DictUtils.getDictLabel (value.toString (), dict, null);
    }
}
