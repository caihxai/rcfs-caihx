package com.caihx.rcfs.core.config.context;

import com.caihx.rcfs.core.refresh.RefreshScope;
import com.rcfs.caihx.common.bean.RefreshBean;
import com.rcfs.caihx.common.specific.RcfsScope;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;


public class RcfsApplicationContextInitializer implements ApplicationContextInitializer {

    private RefreshScope refreshScope;
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerScope(RefreshBean.SCOPE_NAME,rcfsScope());
    }
    public RcfsScope rcfsScope(){
        if (refreshScope==null){
            refreshScope = new RefreshScope();
            return refreshScope;
        }
        return this.refreshScope;
    }
}
