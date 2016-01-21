<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Temperature extends Model
{
    //
    public $fillable = ['room_id','value'];
}
