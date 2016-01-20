<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Log extends Model
{
    //
    public $fillable = [
        'from', 'action', 'value',
    ];
}
