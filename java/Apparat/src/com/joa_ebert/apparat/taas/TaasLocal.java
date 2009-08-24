/*
 * This file is part of Apparat.
 * 
 * Apparat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Apparat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Apparat. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2009 Joa Ebert
 * http://www.joa-ebert.com/
 * 
 */

package com.joa_ebert.apparat.taas;

import com.joa_ebert.apparat.taas.types.TaasType;
import com.joa_ebert.apparat.taas.types.UnknownType;

/**
 * 
 * @author Joa Ebert
 * 
 */
public final class TaasLocal extends TaasValue
{
	private TaasValue value;
	private boolean isTyped;

	private final int index;
	private final int subscript;

	public TaasLocal( final int index )
	{
		this( index, 0 );
	}

	private TaasLocal( final int index, final int subscript )
	{
		super( UnknownType.INSTANCE );

		isTyped = false;

		this.index = index;
		this.subscript = subscript;
	}

	@Override
	public boolean equals( final Object other )
	{
		if( other instanceof TaasLocal )
		{
			return equals( (TaasLocal)other );
		}

		return false;
	}

	public boolean equals( final TaasLocal other )
	{
		return( index == other.getIndex() && subscript == other.getSubscript() );
	}

	public int getIndex()
	{
		return index;
	}

	public int getSubscript()
	{
		return subscript;
	}

	public TaasValue getValue()
	{
		return value;
	}

	public boolean isTyped()
	{
		return isTyped;
	}

	public TaasLocal newVersion()
	{
		return newVersion( subscript + 1 );
	}

	public TaasLocal newVersion( final int newSubscript )
	{
		final TaasLocal result = new TaasLocal( index, newSubscript );

		result.value = value;

		if( isTyped )
		{
			result.typeAs( getType() );
		}

		return result;
	}

	public void setValue( final TaasValue value )
	{
		this.value = value;
		setType( value.getType() );
		isTyped = true;
	}

	@Override
	public String toString()
	{
		final String typeString = isTyped ? getType().toString() : "(untyped)";

		return "[TaasLocal address: (" + Integer.toString( index ) + ","
				+ Integer.toString( subscript ) + "), type: " + typeString
				+ "]";
	}

	public void typeAs( final TaasType type )
	{
		if( isTyped )
		{
			throw new TaasException(
					"Local register has already been typed to "
							+ type.toString() );
		}

		setType( type );
		isTyped = true;
	}
}
