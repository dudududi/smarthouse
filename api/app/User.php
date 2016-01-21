<?php

namespace App;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Database\Eloquent\Model;

class User extends Authenticatable
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */

public function dormitory()
{
    $this->belongsTo(Dormitory::class);
}
public function printers()
{
    $this->hasMany(Printer::class);
}


public function scopeOfDormitory($query,$dormUser_id)
{     $queryTemp = $query;
    return $queryTemp->where('dorm_id',$dormUser_id);
}

    public $fillable = [
        'name', 'email', 'password','dorm_id'
    ];

    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    public $hidden = [
        'password', 'remember_token',
    ];
}
