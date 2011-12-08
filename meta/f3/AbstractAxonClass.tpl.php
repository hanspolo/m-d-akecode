<?php namespace {{@package.name}}\models;

    abstract class DatabaseElement extends \Axon
    {
    	public function __construct($tableName)
		{
			$this->sync($tableName);
		}        

        public function __toJson()
		{

		}
    }
