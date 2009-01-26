/*
 * Copyright (c) 2008 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.grammar;

import java.lang.reflect.Field;

import com.mysema.query.serialization.OperationPatterns;

/**
 * SqlOps represents an SQL dialect for querydsl-sql
 *
 * @author tiwe
 * @version $Id$
 */
public class SqlOps extends OperationPatterns {
    
    private String count = "count ",
        countStar = "count(*)", 
        dummyTable = "dual",
        select = "select ",
        from = "\nfrom ",
        aliasAs = " ",
        fullJoin = "\nfull join ",
        innerJoin = "\ninner join ",
        join = "\njoin ",
        leftJoin = "\nleft join ",
        on = "\non ",
        where = "\nwhere ",
        groupBy = "\ngroup by ",
        having = "\nhaving ",
        orderBy = "\norder by ",
        desc = " desc ",
        asc = " asc ",
        limit = "\nlimit ",
        offset = "\noffset ",
        union = "\nunion\n";
    
    private String limitTemplate, 
        offsetTemplate, 
        limitOffsetTemplate;
    
    private boolean limitAndOffsetSymbols = true;
    
    {
        add(Ops.NOT, "not %s");
        
        // math
        add(Ops.OpMath.RANDOM, "rand()");
        add(Ops.OpMath.CEIL, "ceiling(%s)");
        add(Ops.OpMath.POWER, "power(%s,%s)");
        
        // date time
        add(Ops.OpDateTime.CURRENT_DATE, "curdate()");
        add(Ops.OpDateTime.CURRENT_TIME, "curtime()");        
    }
    
    public String aliasAs(){
        return aliasAs;
    }
    
    public SqlOps aliasAs(String s){
        aliasAs = s;
        return this;
    }
    
    public String asc() {
        return asc;
    }
    
    public SqlOps asc(String s){
        asc = s;
        return this;
    }
    
    public String count() {
        return count;
    }

    public SqlOps count(String s){
        count = s;
        return this;
    }
    
    public String countStar() {
        return countStar;
    }

    public SqlOps countStar(String s){
        countStar = s;
        return this;
    }
    
    public String desc() {
        return desc;
    }
    
    public SqlOps desc(String s){
        desc = s;
        return this;
    }
    
    public String from() {
        return from;
    }

    public SqlOps from(String s){
        from = s;
        return this;
    }
    
    public String fullJoin() {
        return fullJoin;
    }

    public SqlOps fullJoin(String fullJoin) {
        this.fullJoin = fullJoin;
        return this;
    }
    
    public String groupBy() {
        return groupBy;
    }

    public SqlOps groupBy(String s){
        groupBy = s;
        return this;
    }
    
    public String having() {
        return having;
    }

    public SqlOps having(String s){
        having = s;
        return this;
    }
    
    public String innerJoin() {
        return innerJoin;
    }

    public SqlOps innerJoin(String innerJoin) {
        this.innerJoin = innerJoin;
        return this;
    }
    
    public String join() {
        return join;
    }

    public SqlOps join(String join) {
        this.join = join;
        return this;
    }
    
    public String leftJoin() {
        return leftJoin;
    }
    
    public void leftJoin(String leftJoin) {
        this.leftJoin = leftJoin;
    }

    public String limit() {
        return limit;
    }

    public SqlOps limit(String limit) {
        this.limit = limit;
        return this;
    }

    public String offset() {
        return offset;
    }
    
    public SqlOps offset(String offset) {
        this.offset = offset;
        return this;
    }

    public String orderBy() {
        return orderBy;
    }

    public SqlOps orderBy(String s){
        orderBy = s;
        return this;
    }

    public String select() {
        return select;
    }

    public SqlOps select(String s){
        select = s;
        return this;
    }
    
    public boolean supportsAlias() {
        return true;
    }

    public String union() {
        return union;
    }

    public SqlOps union(String union) {
        this.union = union;
        return this;
    }

    public String where() {
        return where;
    }
    
    public SqlOps where(String s){
        where = s;
        return this;
    }
    
    public String on() {
        return on;
    }
       
    public SqlOps on(String s){
        on = s;
        return this;
    }

    public String dummyTable() {
        return dummyTable;
    }
    
    public SqlOps dummyTable(String dt){
        dummyTable = dt;
        return this;
    }
    
    public String limitOffsetCondition(int limit, int offset){    
        if (limitTemplate == null) throw new UnsupportedOperationException();
        if (offset == 0){
            return String.format(limitTemplate, limit);
        }else if (limit == 0){
            return String.format(offsetTemplate, offset);
        }else{
            return String.format(limitOffsetTemplate, limit, offset, limit + offset);    
        }            
    }
    
    public boolean limitAndOffsetSymbols() {
        return limitAndOffsetSymbols;
    }

    public SqlOps limitAndOffsetSymbols(boolean limitAndOffsetSymbols) {
        this.limitAndOffsetSymbols = limitAndOffsetSymbols;
        return this;
    }
    
    public String offsetTemplate() {
        return offsetTemplate;
    }

    public SqlOps offsetTemplate(String offsetTemplate) {
        this.offsetTemplate = offsetTemplate;
        return this;
    }

    public String limitTemplate() {
        return limitTemplate;
    }

    public SqlOps limitTemplate(String limitTemplate) {
        this.limitTemplate = limitTemplate;
        return this;
    }

    public String limitOffsetTemplate() {
        return limitOffsetTemplate;
    }

    public SqlOps limitOffsetTemplate(String limitOffsetTemplate) {
        this.limitOffsetTemplate = limitOffsetTemplate;
        return this;
    }

    public boolean isLimitAndOffsetSymbols() {
        return limitAndOffsetSymbols;
    }

    public void setLimitAndOffsetSymbols(boolean limitAndOffsetSymbols) {
        this.limitAndOffsetSymbols = limitAndOffsetSymbols;
    }

    public SqlOps newLineToSingleSpace(){
        for (Field field : SqlOps.class.getDeclaredFields()){            
            try {
                if (field.getType().equals(String.class)){
                    field.set(this, field.get(this).toString().replace('\n', ' '));    
                }                
            } catch (Exception e) {
                throw new RuntimeException("error", e);
            }
        }
        return this;
    }
    
    @Override
    public SqlOps toLowerCase(){
        super.toLowerCase();
        for (Field field : SqlOps.class.getDeclaredFields()){            
            try {
                field.set(this, field.get(this).toString().toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("error", e);
            }
        }
        return this;
    }

    @Override
    public SqlOps toUpperCase(){
        super.toUpperCase();     
        for (Field field : SqlOps.class.getDeclaredFields()){            
            try {
                field.set(this, field.get(this).toString().toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("error", e);
            }
        }
        return this;
    }
    
    

}
