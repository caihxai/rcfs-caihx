package com.rcfs.caihx.common.specific;

import org.springframework.beans.factory.config.Scope;

public interface RcfsScope extends Scope {
    public void refreshAll();
}
