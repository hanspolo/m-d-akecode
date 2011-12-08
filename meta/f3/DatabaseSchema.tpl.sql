CREATE TABLE {{@class.name}}
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    
    <M:foreach group="{{@class.columns}}" value="{{@column}}">
        {{@column.name}}
        {{@column.type}} 
        <M:if test="{{@column.unique}}"> UNIQUE </M:if> ,
    </M:foreach>
);
