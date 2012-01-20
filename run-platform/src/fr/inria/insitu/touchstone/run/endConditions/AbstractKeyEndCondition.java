/*   TouchStone run platform is a software to run lab experiments. It is         *
 *   published under the terms of a BSD license (see details below)              *
 *   Author: Caroline Appert (appert@lri.fr)                                     *
 *   Copyright (c) 2010 Caroline Appert and INRIA, France.                       *
 *   TouchStone run platform reuses parts of an early version which were         *
 *   programmed by Jean-Daniel Fekete under the terms of a MIT (X11) Software    *
 *   License (Copyright (C) 2006 Jean-Daniel Fekete and INRIA, France)           *
 *********************************************************************************/
/* Redistribution and use in source and binary forms, with or without            * 
 * modification, are permitted provided that the following conditions are met:   *

 *  - Redistributions of source code must retain the above copyright notice,     *
 *    this list of conditions and the following disclaimer.                      *
 *  - Redistributions in binary form must reproduce the above copyright notice,  *
 *    this list of conditions and the following disclaimer in the documentation  *
 *    and/or other materials provided with the distribution.                     *
 *  - Neither the name of the INRIA nor the names of its contributors   *
 * may be used to endorse or promote products derived from this software without *
 * specific prior written permission.                                            *

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"   *
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE     *
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE    *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE     *
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR           *
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF          *
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS      *
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN       *
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)       *
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE    *
 * POSSIBILITY OF SUCH DAMAGE.                                                   *
 *********************************************************************************/
package fr.inria.insitu.touchstone.run.endConditions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Timer;
import javax.swing.event.DocumentEvent;

import com.illposed.osc.OSCMessage;

import fr.inria.insitu.touchstone.run.Platform;
import fr.inria.insitu.touchstone.run.Platform.EndCondition;
import fr.inria.insitu.touchstone.run.input.Axes;
import fr.inria.insitu.touchstone.run.input.AxesEvent;

/**
 * <b>AbstractEndCondition</b> is the base class of all EndConditions based on keyboard input.
 * They suppose that the default keyboard is defined because they stand on axes names such as Keyboard.A, Keyboard.Space, etc.
 * 
 * @author Caroline Appert
 */
public abstract class AbstractKeyEndCondition implements EndCondition {

	protected int id = -1;
	protected String keyName;
	protected String axisName;
	
	protected AbstractKeyEndCondition(String keyName, int id) {
		this.keyName = keyName;
		axisName = "Keyboard."+keyName;
		this.id = id;
	}
	
	/**
	 * Returns true when the end condition is reached.
	 * @param e the event
	 * @return true when the end condition is reached
	 */
	public abstract boolean isReached(AxesEvent e);
	
	/**
	 * Returns true when the end condition is reached.
	 * @param e the event
	 * @return true when the end condition is reached
	 */
	public boolean isReached(InputEvent e) {
		if(!(e instanceof KeyEvent)) 
			return false;
		else {
//			System.out.println("is criterion reached by this keyevent? --> "+(e.getID() == id)+" && "+KeyEvent.getKeyText(((KeyEvent)e).getKeyCode()).equalsIgnoreCase(keyName));
			return ((e.getID() == id) 
				&& KeyEvent.getKeyText(((KeyEvent)e).getKeyCode()).equalsIgnoreCase(keyName));
		}
	}
	
	/**
	 * @return the set of axis required by this end condition
	 */
	public Axes getRequiredAxes(){
		return new Axes(axisName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void start() {
		Platform.getInstance().addAxesListener(getRequiredAxes(), Platform.getInstance());
	}
	
	public boolean isReached(EventObject e) {
		return false;
	}
	
	public boolean isReached(DocumentEvent e) {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void stop() { }
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isReached(Timer timer, long when) {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isReached(OSCMessage message, long when) {
		return false;
	}
}
