package com.example.idgenerator.service.id;

import com.example.idgenerator.service.IdGenerator;
import com.example.idgenerator.service.id.snowflake.SnowFlakeHost;
import org.springframework.stereotype.Service;


/**
 * @author yushengma
 * 生成Id -- 雪花算法snowflake
 */
@Service("idBySnowFlake")
public class IdGenerateBySnowflake implements IdGenerator {

    @Override
    public long getOrderId() throws Exception {
        return SnowFlakeHost.getNextId();
    }
}
